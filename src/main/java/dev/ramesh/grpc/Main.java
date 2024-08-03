package dev.ramesh.grpc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.InvalidProtocolBufferException;
import dev.ramesh.grpc.models.Person;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;


@Slf4j
public class Main {

   static ObjectMapper mapper = new ObjectMapper();

    @Builder
    record Person(int id, String firstName,String lastName,String email, Long phoneNumber){

    }


    public static void main(String[] args) throws IOException, RuntimeException {
        var protoPerson = dev.ramesh.grpc.models.Person.newBuilder()
                .setId(1)
                .setFirstName("Ramesh")
                .setLastName("Maharaddi")
                .setEmail("myemail@gmail.com")
                .setPhoneNumber(88776655L)
                .build();


        var jsonPerson = new Person.PersonBuilder()
                .id(1)
                .firstName("Ramesh")
                .lastName("Maharaddi")
                .email("myemail@gmail.com")
                .phoneNumber(88776655L).build();

        System.out.println("Proto Person "+ protoPerson);
        System.out.println("Json Person "+ jsonPerson);

        for(int i=0; i<5; i++){
           runTest("Json Test", ()-> json(jsonPerson));
        }

        System.out.println("----------------------------");
        for(int i=0; i<5; i++){
            runTest("Proto Test", ()-> proto(protoPerson));
        }


    }

    private static void proto(dev.ramesh.grpc.models.Person person){

        try {
            var bytes = person.toByteArray();
           // log.info("Size of Proto Data {} ", bytes.length);
            dev.ramesh.grpc.models.Person.parseFrom(bytes);
        } catch (InvalidProtocolBufferException e) {
            throw new RuntimeException(e);
        }
    }


    private static void json(Person person)  {
        byte[] bytes = null;
        try {
            bytes = mapper.writeValueAsBytes(person);
            //log.info("Size of Json Data {} ", bytes.length);
            mapper.readValue(bytes, Person.class );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static void runTest(String testName, Runnable runnable){
        var start  = System.currentTimeMillis();

        for(int i=0; i<5_000_000; i++){
            runnable.run();
        }
        var end  = System.currentTimeMillis();
        log.info("Time taken {} : {} ", testName, end-start);
    }
}