import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    try {
      ArrayList<Item> gamesList = new ArrayList<Item>();
      MenuBuilder menu = new MenuBuilder("[%d] %s\n");
      menu.addOption("Ler Arquivo");
      menu.addOption("Ordenar por categoria");
      menu.addOption("Ordenar por avaliação");
      menu.addOption("Sair");

      Scanner scanner = new Scanner(System.in);
      String option;
      do {
        menu.clear();
        menu.draw();
        System.out.printf("Digite sua opção: ");
        option = scanner.nextLine();
        String fileName;
        switch (option) {
          case "1":
            gamesList = Main.readData("data/JogosDesordenados.csv");
            break;
          case "2":
            if (gamesList.size() == 0) {
              System.out.println("Você não leu nenhum arquivo ainda");
              Main.waitUserInteraction(scanner);
              continue;
            }
            Item.orderByCategory(gamesList);
            for (Item game : gamesList)
              System.out.printf("%-20s %-20s\n", game.getName(), game.getCategory());
            fileName = "JogosOrdenadosporCategoria.csv";
            try {
              Main.saveDataInFile(gamesList, fileName);
              System.out.printf("O arquivo '%s' foi criado\n", fileName);
              Main.waitUserInteraction(scanner);
            } catch (IOException error) {
              System.out.println("O arquivo não pode ser criado");
              error.printStackTrace();
            }
            break;
          case "3":
            if (gamesList.size() == 0) {
              System.out.println("Você não leu nenhum arquivo ainda");
              Main.waitUserInteraction(scanner);
              continue;
            }
            Item.orderByRating(gamesList);
            fileName = "JogosOrdenadosporAvaliacao.csv";
            try {
              for (Item game : gamesList)
                System.out.printf("%-40s %-2.1f\n", game.getName(), game.getRating());
              Main.saveDataInFile(gamesList, fileName);
              Main.waitUserInteraction(scanner);
            } catch (IOException error) {
              System.out.printf("O arquivo '%s' foi criado\n", fileName);
              error.printStackTrace();
            }
            break;
        }
      } while (!option.equalsIgnoreCase("4"));
    } catch (FileNotFoundException error) {
      System.out.println("Arquivo não encontrado");
      error.printStackTrace();
    } catch (UnsupportedEncodingException error) {
      System.out.println("Encodificação não suportada");
      error.printStackTrace();
    }
  }

  public static void saveDataInFile(ArrayList<Item> gameList, String filePath) throws IOException {
    OutputStreamWriter myWriter = new OutputStreamWriter(
        new FileOutputStream(filePath),
        Charset.forName("UTF-8").newEncoder());
    String content = "";
    for (Item game : gameList)
      content += game.getName() + "," + game.getCategory() + "," + game.getRating() + "\n";
    myWriter.write(content);
    myWriter.close();
  }

  public static void waitUserInteraction(Scanner scanner) {
    System.out.println("Pressione qualquer tecla para voltar...");
    scanner.nextLine();
  }

  public static ArrayList<Item> readData(String path) throws FileNotFoundException, UnsupportedEncodingException {
    BufferedReader file = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
    Scanner sca = new Scanner(file);
    sca.useDelimiter("\n");
    ArrayList<Item> result = new ArrayList<Item>();
    while (sca.hasNextLine()) {
      String[] data = sca.nextLine().split(",");
      String name = data[0];
      String category = data[1];
      double rating = Double.parseDouble(data[2]);
      result.add(new Item(name, category, rating));
    }
    sca.close();
    return result;
  }
}