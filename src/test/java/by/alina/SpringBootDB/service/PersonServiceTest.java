package by.alina.SpringBootDB.service;

import by.alina.SpringBootDB.assembler.ProductAssembler;
import by.alina.SpringBootDB.model.Person;
import by.alina.SpringBootDB.repositorie.PersonRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @InjectMocks
    private PersonService personService;

    @Mock
    private PersonRepository personRepository;

    //вроде как работает и без этого, но я уверена, что опять что-то косанула
    @Mock
    private ProductAssembler productAssembler;

    private Person person;
    private List<Person> personList;

    @BeforeEach
    public void setUp(){
        person = new Person(1L, "Voldi", 105);
    }

    @AfterEach
    public void tearDown(){
        person = null;
        personList = null;
    }

    @Test
    public void whenGetAllPerson_thenReturnAllPerson(){
        //given
        personList = Arrays.asList(person);
        when(personRepository.findAll()).thenReturn(personList);

        //when
        List<Person> actualPersonList = personService.getPeople();

        //then
        assertEquals(personList, actualPersonList);
        verify(personRepository,times(1)).findAll();
    }

    @Test
    public void whenGetPeopleById_thenReturnPersonOfThatId(){
        //given
        when(personRepository.findById(person.getId())).thenReturn(Optional.of(person));

        //when
        Person actualPerson = personService.getPerson(person.getId());

        //then
        assertEquals(person, actualPerson);
        verify(personRepository, times(1)).findById(person.getId());
    }

    @Test
    public void whenSavePerson_thenReturnPerson(){
        //given
        when(personRepository.save(person)).thenReturn(person);

        //when
        Person actualPerson = personService.addPerson(person);

        //then
        assertEquals(person, actualPerson);
        verify(personRepository,times(1)).save(person);
    }

    //в этом тесте я не уверена
    @Test
    public void whenUpdatePerson_thenReturnUpdatePerson(){
        //given
        Long id = 1L;
        when(personRepository.findById(id)).thenReturn(Optional.of(person));
        when(personRepository.save(person)).thenReturn(person);

        //when
        Person getPerson = personService.getPerson(id);
        getPerson.setName("Harry");
        getPerson.setAge(67);

        Person upPerson = personService.updatePerson(getPerson, id);

        //then
        assertEquals("Harry", upPerson.getName());
        assertEquals(67, upPerson.getAge());
        assertEquals(1, upPerson.getId());
    }

    @Test
    public void whenDeletePersonById_thenDeletePerson(){
        //given
        doNothing().when(personRepository).deleteById(person.getId());

        //when
        personService.deletePerson(person.getId());

        //then
        verify(personRepository,times(1)).deleteById(person.getId());
    }
}
