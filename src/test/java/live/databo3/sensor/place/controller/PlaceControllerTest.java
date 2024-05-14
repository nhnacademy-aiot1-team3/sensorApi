package live.databo3.sensor.place.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import live.databo3.sensor.place.dto.PlaceDto;
import live.databo3.sensor.place.dto.PlaceRequest;
import live.databo3.sensor.place.dto.PlaceResponse;
import live.databo3.sensor.place.service.PlaceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlaceController.class)
class PlaceControllerTest {

    @MockBean
    private PlaceService placeService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void createPlace() throws Exception {
        PlaceResponse placeResponse = new PlaceResponse(1, "testPlace");
        given(placeService.registerPlace(anyInt(), any())).willReturn(placeResponse);
        mockMvc.perform(post("/api/sensor/org/1/place")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new PlaceRequest())))
                .andExpect(status().isCreated())
                .andExpect(content().string(objectMapper.writeValueAsString(placeResponse)));
    }

    @Test
    void getPlaceList() throws Exception {
        List<PlaceDto> list = new ArrayList<>();
        given(placeService.getPlaces(anyInt())).willReturn(list);
        mockMvc.perform(get("/api/sensor/org/1/place")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(list)));
    }

    @Test
    void modifyPlace() throws Exception {
        PlaceResponse placeResponse = new PlaceResponse(1, "testPlace");
        given(placeService.modifyPlace(anyInt(), anyInt(), any())).willReturn(placeResponse);
        mockMvc.perform(put("/api/sensor/org/1/place/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new PlaceRequest())))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(placeResponse)));
    }

    @Test
    void deletePlace() throws Exception {
        mockMvc.perform(delete("/api/sensor/org/1/place/1"))
                .andExpect(status().isOk());
    }
}
