package br.com.pucpr.shoppinglist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import br.com.pucpr.shoppinglist.data.ShoppingList
import br.com.pucpr.shoppinglist.repository.ShoppingListRepository
import br.com.pucpr.shoppinglist.viewmodel.ShoppingListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import java.util.Date

@ExperimentalCoroutinesApi
class ShoppingListViewModelTest {

    private val testScheduler = TestCoroutineScheduler()
    private val fixedDate = Date(1732058137686)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: ShoppingListViewModel

    @Mock
    private lateinit var repository: ShoppingListRepository

    @Mock
    private lateinit var observer: Observer<List<ShoppingList>>

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        //Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.openMocks(this)
        val testShoppingLists = MutableLiveData<List<ShoppingList>>()
        testShoppingLists.value = listOf(
            ShoppingList(name = "Test List 1", dateCreated = fixedDate),
            ShoppingList(name = "Test List 2", dateCreated = fixedDate)
        )

        whenever(repository.allShoppingLists).thenReturn(testShoppingLists)
        viewModel = ShoppingListViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `insert should add a new shopping list`() = runTest {
        val shoppingList = ShoppingList(name = "Test", dateCreated = fixedDate)
        viewModel.insert(shoppingList)
        advanceUntilIdle()
        verify(repository).insert(shoppingList)
    }

    @Test
    fun `delete should remove a shopping list`() = runTest {
        val shoppingList = ShoppingList(id = 1, name = "Test")
        viewModel.delete(shoppingList)
        advanceUntilIdle()
        verify(repository).delete(shoppingList)
    }

    @Test
    fun `getAllShoppingLists should return live data`() {
        val observer = mock<Observer<List<ShoppingList>>>()

        viewModel.allShoppingLists.observeForever(observer)

        verify(observer).onChanged(
            listOf(
                ShoppingList(name = "Test List 1", dateCreated = fixedDate),
                ShoppingList(name = "Test List 2", dateCreated = fixedDate)
            )
        )

        viewModel.allShoppingLists.removeObserver(observer)
    }

    private fun <T> mockLiveData(data: T) = MutableLiveData<T>().apply { value = data }
}