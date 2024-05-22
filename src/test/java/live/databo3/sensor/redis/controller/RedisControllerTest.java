package live.databo3.sensor.redis.controller;

import live.databo3.sensor.redis.service.RedisReloader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RedisController.class)
class RedisControllerTest {

    @MockBean
    private RedisReloader redisReloader;

    @Autowired
    MockMvc mockMvc;

    @Test
    void reloadRedisTest() throws Exception {
        mockMvc.perform(get("/org/testOrganization/config"))
                .andExpect(status().isOk());
    }

    @Test
    void reloadSensorTypesTest() throws Exception {
        mockMvc.perform(get("/sensorTypes"))
                .andExpect(status().isOk());
    }
}
