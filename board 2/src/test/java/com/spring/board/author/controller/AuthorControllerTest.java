package com.spring.board.author.controller;

//WebMvcTest | 를 이용해서 Controller계층을 테스트. 모든 스프링빈을 생성하고 주입하지는 않음.
//@WebMvcTest(AuthorController.class)
//
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class AuthorControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc; //가짜사용자
//
//    @MockBean
//    private AuthorService authorService; //가짜 서비스
//    @Test
//    @WithMockUser // security 의존성 추가 필요
//    void authorDetailTest() throws Exception {
//
//        AuthorDetailResDto authorDetailResDto = new AuthorDetailResDto();
//        authorDetailResDto.setName("testname");
//        authorDetailResDto.setEmail("testemail");
//        authorDetailResDto.setPassword("1234");
//        Mockito.when(authorService.findAuthorDetail(1L)).thenReturn(authorDetailResDto);
//
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/author/1/circle/dto"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(jsonPath("$.name",authorDetailResDto.getName()));
//    }
//}
