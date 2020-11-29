package co.grove.storefinder.viewmodel

import androidx.lifecycle.MutableLiveData
import co.grove.storefinder.network.GeocodeLocations
import co.grove.storefinder.ui.MainActivityInterface
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

@ExperimentalCoroutinesApi
class MainViewModelTest {

    val dispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onFindStoreClicked calls onEmptyAddress if address is empty`() {
        // GIVEN
        val viewModel = MainViewModel()
        val mockLiveData = mock(MutableLiveData::class.java) as MutableLiveData<String>
        val mockInterface = mock(MainActivityInterface::class.java)
        viewModel.activityInterface = mockInterface
        viewModel.addressField = mockLiveData
        whenever(mockLiveData.value).thenReturn("")

        // WHEN
        viewModel.onFindStoreClicked()

        // THEN
        verify(mockInterface).onEmptyAddress()
    }

    @Test
    fun `convertGeocodeDataToLatLongPair handles nulls`() {
        // GIVEN
        val mockGeocodeLocations = mock(GeocodeLocations::class.java)
        whenever(mockGeocodeLocations.lat).thenReturn(null)
        whenever(mockGeocodeLocations.lon).thenReturn(null)
        val viewModel = MainViewModel()

        // WHEN
        val pair = viewModel.convertGeocodeLocationsToLatLongPair(mockGeocodeLocations)

        // THEN
        assert(pair == null)
    }
}
