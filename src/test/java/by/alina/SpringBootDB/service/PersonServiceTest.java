package by.alina.SpringBootDB.service;

import by.alina.SpringBootDB.model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonService personService;

//    данную конструкцию видела в одном из примеров
//    Для чего это?
//    -------
//    @Autowired
//    @InjectMocks
//    private PersonService personService;

    private Person person1;
    private Person person2;

    List<Person> personList;

    @BeforeEach
    public void setUp(){
        personList = new ArrayList<>();

        person1 = new Person(100L, "Alla", 86);
        person2 = new Person(101L, "Peter", 18);

        personList.add(person1);
        personList.add(person2);
    }

    @AfterEach
    public void tearDown(){
        person1 = null;
        person2 = null;
        personList = null;
    }

    @Test
    public void whenSavePerson_shouldReturnPerson(){
        when(personService.addPerson(person1)).thenReturn(person1);
        assertEquals(person1, personService.addPerson(person1));
        verify(personService,times(1)).addPerson(person1);
    }

    @Test
    public void whenGetAllPerson_shouldReturnAllPerson(){

        when(personService.getPeople()).thenReturn(personList);
        assertEquals(personList, personService.getPeople());
        verify(personService,times(1)).getPeople();
    }

    @Test
    public void whenGetPersonById_shouldReturnPersonOfThatId(){
        when(personService.getPerson(person1.getId())).thenReturn(person1);
        assertEquals(person1, personService.getPerson(person1.getId()));
        //assertThat(personService.getPerson(person1.getId())).isEqualTo(person1);
    }

    @Test
    public void whenDeleteById_shouldDeleteThePerson(){
        doNothing().when(personService).deletePerson(person1.getId());
        personService.deletePerson(person1.getId());
        verify(personService,times(1)).deletePerson(person1.getId());
    }
}