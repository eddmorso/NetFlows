import java.io.*;
import java.util.*;

class Top{

    ArrayList<NetFlow> findTopReceivers(ArrayList<NetFlow> netFlows){

        //ArrayList<NetFlow> results = new ArrayList<>();
        Collections.sort(netFlows, new ReceiversComparator());

//        for(int i = 0; i < 10; i++){
//            results.add(netFlows.get(i));
//        }

        return netFlows;//results;
    }

    ArrayList<NetFlow> findTopTransmitters(ArrayList<NetFlow> netFlows){

        Collections.sort(netFlows, new TransmittersComparator());

        return netFlows;
    }

    void findTopProtocols(ArrayList<NetFlow> netFlows){

        Collections.sort(netFlows, new ProtocolsComparator());

        LinkedHashMap<Integer, Integer> linkedHashMap = new LinkedHashMap<>();
        int times = 0;
        for(int i = 0; i < netFlows.size() - 1; i++){
            if(netFlows.get(i).protocolNumber == netFlows.get(i + 1).protocolNumber){
                times++;
            }else {
                linkedHashMap.put(netFlows.get(i).protocolNumber, times);
                times = 0;
            }
        }

        ArrayList<Integer> timesList = new ArrayList<>(linkedHashMap.values());
        Collections.sort(timesList, Collections.reverseOrder());

        for(int i = 0; i < 3; i++){
            for(int key : linkedHashMap.keySet()){
                if(linkedHashMap.get(key).equals(timesList.get(i))){
                    System.out.println(key + " - " + timesList.get(i) + " times.");
                }
            }
        }
    }

    void findTopApps(String fileName) throws IOException{

        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
        ArrayList<String> arrayList = new ArrayList<>();
        String line;
        Scanner scanner;
        int index = 0;

        while((line = bufferedReader.readLine()) != null){

            scanner = new Scanner(line);
            scanner.useDelimiter(",");

            while(scanner.hasNext()){

                String data = scanner.next();

                if(index == 5 && !data.equals("applicationName") && !data.equals("")){
                    arrayList.add(data);
                    break;
                }
                index++;
            }
            index = 0;
        }

        Collections.sort(arrayList);
        LinkedHashMap<String, Long> linkedHashMap = new LinkedHashMap<>();
        long times = 0;

        for(int i = 0; i < arrayList.size() - 1; i++){

            if(arrayList.get(i).equals(arrayList.get(i + 1))){
                times++;
            }else {
                linkedHashMap.put(arrayList.get(i), times);
                times = 0;
            }
        }

        ArrayList<Long> timesList = new ArrayList<>(linkedHashMap.values());
        Collections.sort(timesList, Collections.reverseOrder());

        for(int i = 0; i < 10; i++){
            for(String key : linkedHashMap.keySet()){
                if(linkedHashMap.get(key).equals(timesList.get(i))){
                    System.out.println(key);
                }
            }
        }

        bufferedReader.close();
    }
}
