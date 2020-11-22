package co.grove.storefinder.model

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Test
import org.mockito.Mockito.mock
import java.io.BufferedReader

class CSVFileTest {

    private val firstLine = "Crystal,SWC Broadway & Bass Lake Rd,5537 W Broadway Ave,Crystal,MN,55428-3507,45.0521539,-93.364854,Hennepin County"
    private val secondLine = "Duluth,SEC Hwy 53 & Burning Tree Rd,1902 Miller Trunk Hwy,Duluth,MN,55811-1810,46.808614,-92.1681479,St Louis County"

    private val missingCountyLine = "Duluth,SEC Hwy 53 & Burning Tree Rd,1902 Miller Trunk Hwy,Duluth,MN,55811-1810,46.808614,-92.1681479"

    @Test
    fun `Test ParseStoreList parses stores`() {
        // GIVEN
        val reader = mock(BufferedReader::class.java)
        val csvFile = CSVFile(reader)

        whenever(reader.readLine())
                .thenReturn(firstLine)
                .thenReturn(secondLine)
                .thenReturn(null)

        // WHEN
        val storeList = csvFile.parseStoreList()

        // THEN
        assert(storeList.size == 2)
    }

    @Test
    fun `Test Parses store missing county`() {
        // GIVEN
        val reader = mock(BufferedReader::class.java)
        val csvFile = CSVFile(reader)

        whenever(reader.readLine())
                .thenReturn(missingCountyLine)
                .thenReturn(null)

        // WHEN
        val storeList = csvFile.parseStoreList()

        // THEN
        assert(storeList.size == 1)

    }
}