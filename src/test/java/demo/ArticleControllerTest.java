package demo;

import static junit.framework.Assert.assertNull;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import org.domain.restfulapi.service.ArticleService;
import org.domain.restfulapi.entity.Article;
import org.domain.restfulapi.exception.ArticleNotFoundException;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringBootMvcRestfulApiArticle2Application.class)
public class ArticleControllerTest {

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;
    
    @Autowired
    private ArticleService articleServiceMock;
 
    @Autowired
    private WebApplicationContext webApplicationContext;
    
    @Before
    public void setUp() {
        Mockito.reset(articleServiceMock);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    
    @Test
    public void findAll_ArticlesFound_ShouldReturnFoundArticleEntries() throws Exception {
    	DateTime now = DateTime.now();
    	Article first = new Article();
        first.setId(1);
        first.setTitleArticle("Title_1");
        first.setBodyArticle("Body_Article_1");
        first.setAuthor("Author_1");
        first.setDateCreate(now);
        first.setDateUpdate(now);
       
        Article second = new Article();
        second.setId(2);
        second.setTitleArticle("Title_2");
        second.setBodyArticle("Body_Article_2");
        second.setAuthor("Author_2");
        second.setDateCreate(now);
        second.setDateUpdate(now);
 
        when(articleServiceMock.findAll()).thenReturn(Arrays.asList(first, second));
 
        mockMvc.perform(get("/articles/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].titleArticle", is("Title_1")))
                .andExpect(jsonPath("$[0].bodyArticle", is("Body_Article_1")))
                .andExpect(jsonPath("$[0].author", is("Author_1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].titleArticle", is("Title_2")))
                .andExpect(jsonPath("$[1].bodyArticle", is("Body_Article_2")))
                .andExpect(jsonPath("$[1].author", is("Author_2")));
 
        verify(articleServiceMock, times(1)).findAll();
        verifyNoMoreInteractions(articleServiceMock);
    }
    
    @Test
    public void findById_ArticleEntryNotFound_ShouldReturnHttpStatusCode404() throws Exception {
        when(articleServiceMock.findById(1)).thenThrow(new ArticleNotFoundException(""));
 
        mockMvc.perform(get("/articles/{id}", 1))
                .andExpect(status().isNotFound());
 
        verify(articleServiceMock, times(1)).findById(1L);
        verifyNoMoreInteractions(articleServiceMock);
    }
    
    @Test
    public void findById_ArticleEntryFound_ShouldReturnFoundArticleEntry() throws Exception {
    	DateTime now = DateTime.now();
    	Article found = new Article();
        found.setId(1);
        found.setTitleArticle("Title_1");
        found.setBodyArticle("Body_Article_1");
        found.setAuthor("Author_1");
        found.setDateCreate(now);
        found.setDateUpdate(now);
 
        when(articleServiceMock.findById(1)).thenReturn(found);
 
        mockMvc.perform(get("/articles/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.titleArticle", is("Title_1")))
                .andExpect(jsonPath("$.bodyArticle", is("Body_Article_1")))
                .andExpect(jsonPath("$.author", is("Author_1")));
 
        verify(articleServiceMock, times(1)).findById(1L);
        verifyNoMoreInteractions(articleServiceMock);
    }
    
    @Test
    public void add_NewArticleEntry_ShouldAddArticleEntryAndReturnAddedEntry() throws Exception {
        DateTime now = DateTime.now();
    	
    	Article article1 = new Article();
        article1.setTitleArticle("Title_1");
        article1.setBodyArticle("Body_Article_1");
        article1.setAuthor("Author_1"); 
        article1.setDateCreate(now);
        article1.setDateUpdate(now);
        
        Article article2 = new Article();
        article2.setId(1);
        article2.setTitleArticle("Title_1");
        article2.setBodyArticle("Body_Article_1");
        article2.setAuthor("Author_1"); 
        article2.setDateCreate(now);
        article2.setDateUpdate(now);
 
        when(articleServiceMock.save(article1)).thenReturn(article2);
 
        mockMvc.perform(post("/articles")
                .contentType(contentType)
                .content(convertObjectToJsonBytes(article1))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.titleArticle", is("Title_1")))
                .andExpect(jsonPath("$.bodyArticle", is("Body_Article_1")))
                .andExpect(jsonPath("$.author", is("Author_1")));
 
        ArgumentCaptor<Article> articleCaptor = ArgumentCaptor.forClass(Article.class);
        verify(articleServiceMock, times(1)).save(articleCaptor.capture());
        verifyNoMoreInteractions(articleServiceMock);
 
        Article articleArgument = articleCaptor.getValue();
        assertNull(articleArgument.getId());
        assertThat(articleArgument.getTitleArticle(), is("Title_1"));
        assertThat(articleArgument.getBodyArticle(), is("Body_Article_1"));
        assertThat(articleArgument.getAuthor(), is("Author_1"));
    }
    
    private static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }

}
