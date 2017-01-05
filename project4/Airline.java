import java.util.*;
import java.io.*;
import java.lang.NullPointerException;
import java.lang.ArrayIndexOutOfBoundsException;
import java.lang.IndexOutOfBoundsException;
import java.util.ConcurrentModificationException;
import java.util.InputMismatchException;

public class Airline{
     static EdgeWeightedGraph routes; //edge-weighted routes
     static EdgeWeightedDigraph dirRoutes; //di edge-wighted routs
     static Graph nwRoutes; //no weight routs
     public static String[] cities; //links city with int
     static Scanner kbd = new Scanner(System.in);

     public static void main(String[] args){
          String startingCity;
          String endingCity;
          int start;
          int end;
          String st;
          String en;
          int distance;
          double price;
          String output;
          String fileName;
          int max;
          double maxPrice;
          double min_flight = Double.MAX_VALUE;

          System.out.println("Please enter a file to load");
          fileName = kbd.next();
          try{
               BufferedReader br = new BufferedReader(new FileReader(fileName));
               int vertexes = Integer.parseInt(br.readLine());
               cities = new String[vertexes];
               routes = new EdgeWeightedGraph(vertexes);
               dirRoutes = new EdgeWeightedDigraph(vertexes);
               nwRoutes = new Graph(vertexes);

               for(int i = 0; i < cities.length; i++){
                    cities[i] = br.readLine();
               }
               while(br.ready()){
                    //1 2 127 200.00
                    String[] routeData = br.readLine().split(" ");
                    if(routeData.length  > 4){
                         continue;
                    }
                    start = Integer.parseInt(routeData[0])-1;
                    end = Integer.parseInt(routeData[1])-1;
                    distance = Integer.parseInt(routeData[2]);
                    price = Double.parseDouble(routeData[3]);
                    if(price < min_flight){
                         min_flight = price;
                    }
                    Edge route = new Edge(start, end, price,distance);
                    DirectedEdge dirRoute = new DirectedEdge(start, end, price, distance);
                    routes.addEdge(route);
                    dirRoutes.addEdge(dirRoute);
                    nwRoutes.addEdge(start, end);
               }
               br.close();
          }
          catch(IOException e){
               System.err.println("Caught IOException: " + e.getMessage());
               System.exit(0);
          }
          int input = 0;
          do{
               try{
                    System.out.println("What Would You Like To Do:\n"
                              + "1. List All Routes\n"
                              + "2. Display MST\n"
                              + "3. Find shortest distance between two cities\n"
                              + "4. Find cheapest flight between two cities\n"
                              + "5. Find route with least hops between two cities\n"
                              + "6. All trips cheaper than input dollar ammount\n"
                              + "7. Add a route\n"
                              + "8. Remove a route\n"
                              + "9. Quit");
                              input = kbd.nextInt();
                              switch (input){
                                   case 1: //list all routes
                                        routes.print();
                                        break;
                                   case 2: //display MST based on distances
                                        //Prim's or Kruskal's
                                        routes.setCompareType("distance");
                                        KruskalMST  mst = new KruskalMST(routes);
                                        mst.print();
                                        break;
                                   case 3: //Find shortest distane between two cities
                                        //Dijkstra
                                        System.out.println("What city do you want to start from");
                                        startingCity = kbd.next();
                                        System.out.println("What city do you want to end at");
                                        endingCity = kbd.next();
                                        dirRoutes.setCompareType("distance");
                                        DijkstraSP_distance shortDist = new DijkstraSP_distance(dirRoutes, getCityIndex(startingCity));
                                        shortDist.print(getCityIndex(endingCity));
                                        break;
                                   case 4://find cheapest route between two cities
                                        //Dijkstra
                                        System.out.println("What city do you want to start from");
                                        startingCity = kbd.next();
                                        System.out.println("What city do you want to end at");
                                        endingCity = kbd.next();
                                        dirRoutes.setCompareType("price");
                                        DijkstraSP_price shortPrice = new DijkstraSP_price(dirRoutes, getCityIndex(startingCity));
                                        shortPrice.print(getCityIndex(endingCity));
                                        break;
                                   case 5: //find route with least hops between two cities
                                        //Breath First Search
                                        System.out.println("What city do you want to start from");
                                        startingCity = kbd.next();
                                        System.out.println("What city do you want to end at");
                                        endingCity = kbd.next();
                                        BreadthFirstPaths bfs = new BreadthFirstPaths(nwRoutes, getCityIndex(startingCity));
                                        bfs.print(getCityIndex(endingCity));
                                        break;
                                   case 6: //all trips shorter than specified dollar ammount
                                        System.out.println("Please enter the maximum price you wish to spend");
                                        maxPrice = kbd.nextDouble();
                                        //dirRoutes.maxPrice(maxPrice);
                                        CC cc = new CC(routes, 0.0, maxPrice);
                                        break;
                                   case 7: //add route
                                        System.out.println("Please enter name of starting city");
                                        st = kbd.next();
                                        start = getCityIndex(st);
                                        System.out.println("Please enter name of ending city");
                                        en = kbd.next();
                                        end = getCityIndex(en);
                                        System.out.println("Please enter a distance");
                                        distance = kbd.nextInt();
                                        System.out.println("Please enter price");
                                        price = kbd.nextDouble();

                                        if(start == -1 || end == -1){
                                             System.out.println("City was not listed, try again");
                                             break;
                                        }
                                        routes.addEdge(new Edge(start, end, price, distance));
                                        dirRoutes.addEdge(new DirectedEdge(start, end, price, distance));
                                        nwRoutes.addEdge(start, end);
                                        break;
                                   case 8: //remove route
                                        System.out.println("Please enter name of starting city");
                                        st = kbd.next();
                                        start = getCityIndex(st);
                                        System.out.println("Please enter name of ending city");
                                        en = kbd.next();
                                        end = getCityIndex(en);

                                        if(start == -1 || end == -1){
                                             System.out.println("City was not listed, try again");
                                             break;
                                        }
                                        routes.removeEdge(new Edge(start, end));
                                        dirRoutes.removeEdge(new DirectedEdge(start, end));
                                        nwRoutes.removeEdge(start, end);
                                        break;
                                   case 9: //quit
                                        dirRoutes.setCompareType("price");
                                        break;
                                   default:
                                        System.out.println("Please enter valid option");
                         }
                    }
                    catch(NullPointerException e){
                         System.err.println("Caught NullPointerException: " + e.getMessage());
                         System.out.println("This must mean no route exists for your query");
                    }
                    catch(ArrayIndexOutOfBoundsException e){
                         System.err.println("Caught ArrayIndexOutOfBoundsException: " + e.getMessage());
                    }
                    catch(ConcurrentModificationException e){
                         System.err.println("Caught ConcurrentModificationException: " + e.getMessage());
                         System.out.println("Try again");
                    }
                    catch(IndexOutOfBoundsException e){
                         System.err.println("Caught IndexOutOfBoundsException: " + e.getMessage());
                    }
                    catch(InputMismatchException e){
                         System.err.println("Caught InputMismatchException: " + e.getMessage());
                    }
          }while(input != 9);
          try{
               // System.out.println("Please enter file name (Omit the \".txt\")");
               // output = kbd.next();
               // output += ".txt";
               PrintWriter writer = new PrintWriter(fileName);
               writer.println(cities.length);
               for(String s: cities){
                    writer.println(s);
               }
               ArrayList<LinkedList<DirectedEdge>> adj = dirRoutes.adj();
               for(LinkedList<DirectedEdge> l: adj){
                    for(DirectedEdge e: l){
                         writer.println((e.from()+1) + " " + (e.to()+1) + " " + e.distance() + " " + e.price());
                    }
               }
               writer.close();
          }
          catch(Exception e){
               System.err.println("Caught Exception: " + e.getMessage());
          }
     }

     public static String getCity(int i){
          return cities[i];
     }
     public static int getCityIndex(String s){
          for(int i = 0; i < cities.length; i++){
               if (cities[i].equals(s)){
                    return i;
               }
          }
          return -1;
     }
}
