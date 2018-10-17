import com.scarlatti.Mapper
import org.junit.Test

import java.nio.file.Files
import java.nio.file.Paths

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Tuesday, 10/16/2018
 */
class MapperTest {

    @Test
    void useMapperWithSingleField() {
        byte[] bytes = Files.readAllBytes(Paths.get("src/test/resources/test1.md"))
        Instance instance = new Mapper().readValueAs(new String(bytes), Instance.class)

        assert instance != null
        assert instance.codeToMap != null
        assert instance.codeToMap.contains("really, really long")
    }

    @Test
    void useMapperWithTwoFields() {
        byte[] bytes = Files.readAllBytes(Paths.get("src/test/resources/test2.md"))
        InstanceWithTwoFields instance = new Mapper().readValueAs(new String(bytes), InstanceWithTwoFields.class)

        assert instance != null
        assert instance.codeToMap != null
        assert instance.codeToMap.contains("really, really long")
        assert instance.moreCodeToMap != null
        assert instance.moreCodeToMap.contains("isJson")
    }

    static class Instance {
        String codeToMap
    }

    static class InstanceWithTwoFields {
        String codeToMap
        String moreCodeToMap
    }
}
