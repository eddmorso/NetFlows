import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Main extends Application {
    static String fileName = "2018-11-20-17-17.csv";
    static String date = "7/13/2018 5:23:22";
    static int num = 10;

    public static void main(String [] args) throws IOException {
        Top top = new Top();

        //receivers
        System.out.println("top 10 receivers: ");
        for(int i = 0; i < num; i++){
            System.out.println(top.findTopReceivers(makeNetFlowList(fileName)).get(i).getBytesIn());
        }
        System.out.println();

        //transmitters
        System.out.println("top 10 transmitters: ");
        for(int i = 0; i < num; i++){
            System.out.println(top.findTopTransmitters(makeNetFlowList(fileName)).get(i).getBytesOut());
        }
        System.out.println();

        //protocols
        System.out.println("top 3 protocols: ");
        top.findTopProtocols(makeNetFlowList(fileName));
        System.out.println();

        //apps
        System.out.println("top 10 applications: ");
        top.findTopApps(fileName);
        System.out.println();

        //specified time calculation
        System.out.println("time calculation");
        calculateData(fileName, date);
        System.out.println();

        launch(args);
    }

    @Override public void start(Stage stage) throws IOException {
        stage.setTitle("Chart of bytes over time");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Time");
        final LineChart<String, Number> lineChart =
                new LineChart<String, Number>(xAxis, yAxis);

        lineChart.setTitle("Top NetFlows");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Receiver");

        ArrayList<NetFlow> overTime = calculateData(fileName, date);
        ArrayList<NetFlow> receivers = new Top().findTopReceivers(overTime);
        ArrayList<NetFlow> transmitters = new Top().findTopTransmitters(overTime);

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Transmitter");

        for (int i = 0; i < receivers.size(); i++) {
            series1.getData().add(new XYChart.Data(receivers.get(i).date.toString(), receivers.get(i).bytesIn));
        }
        for(int i = 0; i < transmitters.size(); i++) {
            series2.getData().add(new XYChart.Data(transmitters.get(i).date.toString(), transmitters.get(i).bytesOut));
        }

        Scene scene  = new Scene(lineChart,800,600);
        lineChart.getData().addAll(series1, series2);

        stage.setScene(scene);
        stage.show();
    }

    private static ArrayList<NetFlow> makeNetFlowList(String fileName)throws IOException {

        ArrayList<NetFlow> netFlowList = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));

        Scanner scanner;
        String line;

        while((line = bufferedReader.readLine()) != null){

            scanner = new Scanner(line);
            scanner.useDelimiter(",");

            NetFlow netFlow = new NetFlow();

            int index = 0;

            while(scanner.hasNext()) {

                String data = scanner.next();

                if (index == 0 && !data.equals("date") && !data.equals("")) {
                    try {
                        data = data.replace(".000000000","");
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
                        Date date = simpleDateFormat.parse(data);
                        netFlow.setDate(date);

                    } catch (ParseException e) {
                        System.out.println(e);
                    }
                }else if(index == 1 && !data.equals("bytesIn") && !data.equals("")){
                    netFlow.setBytesIn(Long.valueOf(data));
                }else if(index == 2 && !data.equals("bytesOut") && !data.equals("")){
                    netFlow.setBytesOut(Long.valueOf(data));
                }else if(index == 3 && !data.equals("packetsIn") && !data.equals("")){
                    netFlow.setPacketsIn(Long.valueOf(data));
                }else if(index == 4 && !data.equals("packetsOut") && !data.equals("")){
                    netFlow.setPacketsOut(Long.valueOf(data));
                }else if(index == 5 && !data.equals("applicationName") && !data.equals("")){
                    netFlow.setAppName(data);
                }else if(index == 6 && !data.equals("destinationAddress") && !data.equals("")){
                    netFlow.setDestinationAddress(data);
                }else if(index == 7 && !data.equals("protocolNumber") && !data.equals("")){
                    netFlow.setProtocolNumber(Integer.valueOf(data));
                }else if(index == 8 && !data.equals("sourceAddress") && !data.equals("")){
                    netFlow.setSourceAddress(data);
                }
                index++;
            }
            netFlowList.add(netFlow);
        }
        return netFlowList;
    }

    static ArrayList<NetFlow> calculateData(String fileName, String dateString)throws IOException{

        ArrayList<NetFlow> results = new ArrayList<>();

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
            Date date = simpleDateFormat.parse(dateString);

            ArrayList<NetFlow> netFlows = makeNetFlowList(fileName);

            double traffic = 0, apps = 0, protocols = 0;

            for (int i = 0; i < netFlows.size(); i++) {
                if (date != null && date.equals(netFlows.get(i).getDate())) {
                    results.add(netFlows.get(i));
                    traffic += netFlows.get(i).getBytesIn() + netFlows.get(i).getBytesOut();
                    apps++;
                    protocols++;
                }
            }
            System.out.println("chosen time: " + date);
            System.out.println();
            System.out.println("total traffic was used: " + traffic);
            System.out.println("total number of used applications: " + apps);
            System.out.println("total number of used protocols: " + protocols);

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("traffic", traffic);
            jsonObject.put("apps", apps);
            jsonObject.put("protocols", protocols);

            FileWriter fileWriter = new FileWriter("jsonText.json");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(jsonObject.toString());
            bufferedWriter.close();

        }catch (ParseException e){
            System.out.println("Wrong type of time..");
            System.out.println(e);
        }
        return results;
    }
}
