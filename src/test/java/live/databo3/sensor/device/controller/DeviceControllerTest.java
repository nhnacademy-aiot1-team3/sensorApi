package live.databo3.sensor.device.controller;

import live.databo3.sensor.device.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(DeviceController.class)
public class DeviceControllerTest {

    @MockBean
    private DeviceService deviceService;

    @Autowired
    MockMvc mockMvc;


}
