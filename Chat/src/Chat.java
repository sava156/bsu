import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Chat {
    public static void main(String args[]) throws IOException {
        int added = 0;
        int deleted = 0;
        int found = 0;
        int foundByAuthor = 0;
        int foundByRegex = 0;
        int foundByDate = 0;
        int foundByLexems = 0;

        boolean exit = false;
        int num;
        String user;
        Scanner scn = new Scanner(System.in);
        ObjectMapper mapper = new ObjectMapper();
        byte[] jsonData = Files.readAllBytes(Paths.get("chat"));
        ArrayList<Message> messages = new ArrayList<Message>();
        try {
            messages = mapper.readValue(jsonData, new TypeReference<ArrayList<Message>>() {
            });
        } catch (JsonMappingException ex) {

        }

        BufferedWriter logw = new BufferedWriter(new FileWriter("log.txt"));
//        for(int i=0;i<student.length;i++)
//            System.out.println(student[i]);

        System.out.println("Username:");
        user = String.valueOf(scn.next());
        while (!exit) {
            System.out.println("");
            System.out.println("");
            System.out.println("1.Send Message");
            System.out.println("2.Show story");
            System.out.println("3.Delete message");
            System.out.println("4.Search by author");
            System.out.println("5.Search by regex");
            System.out.println("6.Search by time");
            System.out.println("7.Search by lexem");
            System.out.println("8.Exit");
            System.out.println("");
            System.out.println("");
            num = scn.nextInt();
            if (num == 1) {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                String toWrite = br.readLine();
                if (toWrite.length() < 140) {
                    Message mes = new Message(UUID.randomUUID().toString(), user, Date.from(Instant.now()).getTime(), toWrite);
                    messages.add(mes);
                    mapper.writeValue(new File("chat"), messages);
                    added++;
                    logw.write("--adding message at: " + Date.from(Instant.now()) + "  total added: " + added);
                    logw.newLine();
                } else {
                    logw.write("!!!Number of symbols in message more than 140!!!");
                    logw.newLine();
                }

                num = 10;
            }
            if (num == 2) {
                messages.forEach(System.out::println);
                found = messages.size();
                logw.write("--looking history at: " + Date.from(Instant.now()) + "  total showed: " + found);
                logw.newLine();
                num = 10;
            }

            if (num == 3) {
                String id;
                System.out.println("ID:");
                id = scn.next();
                for (int i = 0; i < messages.size(); i++)
                    if (Objects.equals(messages.get(i).getId(), id)) {
                        messages.remove(i);
                        deleted++;
                    }
                logw.write("--deleting message at: " + Date.from(Instant.now()) + "  deleted: " + deleted);
                logw.newLine();
                deleted = 0;
                num = 10;
            }
            if (num == 4) {
                String author = String.valueOf(scn.next());
                for (int i = 0; i < messages.size(); i++) {
                    if (messages.get(i).getAuthor().equals(author)) {
                        System.out.println(messages.get(i));
                        foundByAuthor++;
                    }
                }
                logw.write("--searching by author at: " + Date.from(Instant.now()) + "  found: " + foundByAuthor);
                logw.newLine();
                foundByAuthor = 0;
//                foundByAuthor++;
                num = 10;

            }
            if (num == 5) {
                System.out.println("Type regex: ");
                String reg = String.valueOf(scn.next());
                Pattern p = Pattern.compile(reg);
                Matcher m;
                for (Message message : messages) {
                    m = p.matcher(message.getMessage());
                    if (m.matches()) {
                        System.out.println(message);
                        foundByRegex++;
                    }
                    num = 10;
                }
                logw.write("--searching by regex at: " + Date.from(Instant.now()) + "  found: " + foundByRegex);
                logw.newLine();
            }
            if (num == 6) {
                System.out.println("Type starttime(format dd.mm.yyyy hh.mm.ss:");
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                String date = br.readLine();
                SimpleDateFormat sd = new SimpleDateFormat("dd.MM.yyyy HH.mm.ss");
                Long datbegin = null;
                System.out.println("Type endtimetime(format dd.mm.yyyy hh.mm.ss:");
                String enddate = br.readLine();
                Long datend = null;
                try {
                    java.util.Date date1 = sd.parse(date);
                    java.util.Date date2 = sd.parse(enddate);
                    datend = date2.getTime();
                    datbegin = date1.getTime();
                    for (Message message : messages) {
                        if (message.getTimestamp() >= datbegin && message.getTimestamp() <= datend) {
                            System.out.println(message);
                            foundByDate++;
                        }

                    }
                    logw.write("--seatching by time at: "+Date.from(Instant.now())+"  found: ");
                    logw.newLine();
                    foundByDate=0;
                } catch (ParseException e) {
                    logw.write("!!!!Wron date input!!!!!");
                    logw.newLine();
                }
                num = 10;

            }
            if (num == 7) {
                System.out.println("Type lexem:");
                String a = String.valueOf(scn.next());
                for (Message message : messages) {
                    for (int j = 0; j < message.getMessage().length(); j++) {
                        for (int k = j; k < message.getMessage().length(); k++) {
                            if (message.getMessage().substring(j, k).equals(a)) {
                                System.out.println(message);
                                foundByLexems++;
                            }
                        }
                    }
//                    if(messages.get(i).getMessage())
                }
                logw.write("--searching by lexems at: "+Date.from(Instant.now())+"  found: "+foundByLexems);
                logw.newLine();
                foundByLexems=0;
                num = 10;
            }
            if (num == 8){
                logw.close();
                break;
            }
        }
    }
}