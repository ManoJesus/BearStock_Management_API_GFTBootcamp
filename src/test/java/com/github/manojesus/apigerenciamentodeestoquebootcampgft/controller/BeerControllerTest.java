package com.github.manojesus.apigerenciamentodeestoquebootcampgft.controller;

import com.github.manojesus.apigerenciamentodeestoquebootcampgft.DTO.BeerDTO;
import com.github.manojesus.apigerenciamentodeestoquebootcampgft.builderDTO.BeerDTOBuilder;
import com.github.manojesus.apigerenciamentodeestoquebootcampgft.exception.BeerNotFoundException;
import com.github.manojesus.apigerenciamentodeestoquebootcampgft.service.BeerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Collections;

import static com.github.manojesus.apigerenciamentodeestoquebootcampgft.utils.JsonConverterUtils.asJsonString;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class BeerControllerTest {

    private static final String BEER_API_URL_PATH = "/api/v1.0/beers";
    private static final long VALID_BEER_ID = 1L;
    private static final long INVALID_BEER_ID = 2l;
    private static final String BEER_API_SUBPATH_INCREMENT_URL = "/increment";
    private static final String BEER_API_SUBPATH_DECREMENT_URL = "/decrement";


    private MockMvc mockMvc;

    @Mock
    private BeerService beerService;

    @InjectMocks
    private BeerController beerController;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(beerController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenPOSTIsCalledThenBeerIsCreated() throws Exception {
        //given
        BeerDTO toBeSavedBeerDTO = BeerDTOBuilder.builder().build().buildDTO();

        //when
        when(beerService.create(toBeSavedBeerDTO)).thenReturn(toBeSavedBeerDTO);

        //then
        mockMvc.perform(post(BEER_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(toBeSavedBeerDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(toBeSavedBeerDTO.getName())))
                .andExpect(jsonPath("$.brand", is(toBeSavedBeerDTO.getBrand())))
                .andExpect(jsonPath("$.max", is(toBeSavedBeerDTO.getMax())))
                .andExpect(jsonPath("$.quantity", is(toBeSavedBeerDTO.getQuantity())))
                .andExpect(jsonPath("$.beerType", is(toBeSavedBeerDTO.getBeerType().toString())));
    }
    @Test
    void whenPOSTIsCalledWithNullRequiredParameterThenBadRequest() throws Exception {
        //given
        BeerDTO toBeSavedBeerDTO = BeerDTOBuilder.builder().build().buildDTO();
        toBeSavedBeerDTO.setName(null);

        //then
        mockMvc.perform(post(BEER_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(toBeSavedBeerDTO)))
                .andExpect(status().isBadRequest());
    }
    @Test
    void whenGETWithValidNameCalledThenReturnBeer() throws Exception {
        //given
        BeerDTO toBeGotBeerDTO = BeerDTOBuilder.builder().build().buildDTO();

        //when
        when(beerService.searchBeerByName(toBeGotBeerDTO.getName())).thenReturn(toBeGotBeerDTO);

        //then
        mockMvc.perform(get(BEER_API_URL_PATH+"/"+toBeGotBeerDTO.getName())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(toBeGotBeerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is(toBeGotBeerDTO.getName())))
                .andExpect(jsonPath("$.brand",is(toBeGotBeerDTO.getBrand())));
    }

    @Test
    void whenGETCalledWithNameNotRegisteredThenNotFoundIsReturn() throws Exception {
        //given
        BeerDTO toBeGotBeerDTO = BeerDTOBuilder.builder().build().buildDTO();

        //when
        when(beerService.searchBeerByName(toBeGotBeerDTO.getName())).thenThrow(BeerNotFoundException.class);

        //then
        mockMvc.perform(get(BEER_API_URL_PATH+"/"+toBeGotBeerDTO.getName())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }
    @Test
    void whenGETListWithBeerSIsCalledThenOkStatusIsReturn() throws Exception {
        //given
        BeerDTO beerDTO = BeerDTOBuilder.builder().build().buildDTO();

        //when
        when(beerService.searchAllBeers()).thenReturn(Collections.singletonList(beerDTO));

        //then
        mockMvc.perform(get(BEER_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is(beerDTO.getName())))
                .andExpect(jsonPath("$[0].brand", is(beerDTO.getBrand())))
                .andExpect(jsonPath("$[0].beerType", is(beerDTO.getBeerType().toString())));
    }
    @Test
    void whenDELETEIsCalledWithValidIdThenNoContentReturn() throws Exception {
        //when
        doNothing().when(beerService).deleteBeerById(VALID_BEER_ID);

        //then
        mockMvc.perform(delete(BEER_API_URL_PATH+"/"+VALID_BEER_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
    @Test
    void whenDELETEIsCalledWithInvalidIdThenNotFoundReturn() throws Exception {
        //when
        doThrow(BeerNotFoundException.class).when(beerService).deleteBeerById(INVALID_BEER_ID);

        //then
        mockMvc.perform(delete(BEER_API_URL_PATH+"/"+INVALID_BEER_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
