import java.util.ArrayList;

class MenuBuilder {
    private String title;
    private String template;
    private ArrayList<String> options = new ArrayList<String>();

    public MenuBuilder(String template) {
        this.template = template;
    }

    public MenuBuilder(String template, String title) {
        this.template = template;
        this.title = title;
    }

    public void addOption(String text) {
        this.options.add(text);
    }

    public void addOption(int index, String text) {
        this.options.add(index, text);
    }

    public void draw() {
        int index = 0;
        if (this.title != null)
            System.out.println(this.title);
        for (String option : options)
            System.out.printf(this.template, ++index, option);
    }

    public void drawPrompt() {
        System.out.printf("Digite sua opção: ");
    }

    public void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}