import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.BorderFactory;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.ArrayList;
import java.io.File;


public class MyFrame extends JFrame {

    Graphics g;
    protected static ArrayList<Figure> figures = new ArrayList<>();
    JPanel mainPanel;
    JPanel figuresListPanel;
    MyDrawingPanel drawingPanel;


    public MyFrame() {

        super("Графический редактор");
        setSize(900, 600);

        setBackground(Color.WHITE);
        FillComponents();

        g = drawingPanel.getGraphics();


    }


    private Path openFileExplorer() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("src"));

        // Заголовок диалогового окна
        fileChooser.setDialogTitle("Выберите файл с фигурами");

        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            // Пользователь выбрал файл
            File selectedFile = fileChooser.getSelectedFile();
            return selectedFile.toPath();

        }
        return null;
    }
    private void getFiguresFromFile(Path filePath){

        try{
            if(filePath != null){
                switch(filePath.toString().split("\\.")[1]){
                    case "txt":
                        figures.addAll(FileManager.getInstance().Deserialize(filePath.toString()));
                        break;
                    case "json":
                        figures.addAll(FileManager.getInstance().DeserializeJson(filePath.toString()));
                        for(Figure f: figures){
                            System.out.println(f.toString());
                        }
                        break;
                    default:
                        return;
                }
                FullfillFiguresListPanel(figures);

                figuresListPanel.revalidate();
                figuresListPanel.repaint();

                drawingPanel.figures = figures;
                drawingPanel.repaint();
            }
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(mainPanel,
                    "Ошибка:" + e.getMessage(),
                    "Невозможно открыть файл",
                    JOptionPane.INFORMATION_MESSAGE);

        }



    }

    // Метод для закрытия текущего файла
    private void closeCurrentFile(Path filePath) {
        if(filePath != null ){
            if (!FileManager.getInstance().Serialize(figures, filePath)){
                JOptionPane.showMessageDialog(mainPanel,
                        "Ошибка сериализации, невозможно закрыть файл",
                        "Ошибка",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            figures.removeAll(figures);
            setFrameDefault(drawingPanel);
            setFrameDefault(figuresListPanel);
        }
    }

    private void SavePhoto(Path fileName){

        try{
            BufferedImage image = new BufferedImage(drawingPanel.getWidth(), drawingPanel.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = image.createGraphics();
            drawingPanel.paint(g2d);

            String fileNameString = fileName.toString();
            if(!(fileNameString.split("//.")[1].equals("png"))){
                fileNameString +=  ".png";
            }
            File file = new File(fileNameString);

            ImageIO.write(image, "png", file);
            JOptionPane.showMessageDialog(mainPanel,
                    "успешно",
                    "сохранено",
                    JOptionPane.INFORMATION_MESSAGE);

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void FullfillFiguresListPanel(ArrayList<Figure> figures) {

        setFrameDefault(figuresListPanel);
        int i = 1;
        for (Figure f : figures) {
            JCheckBox checkBox = new JCheckBox(i + ") " + f.getName());
            checkBox.putClientProperty("figure", f);
            checkBox.addActionListener(myCheckBoxListener);
            checkBox.setSelected(true);
            figuresListPanel.add(checkBox);
            figuresListPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            i++;
        }
    }

    private void FillComponents(){
        // Главная панель с BorderLayout
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // === ВЕРХНЯЯ ПАНЕЛЬ С КНОПКАМИ ===
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));


        // Панель для кнопок слева
        JPanel leftButtonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        JButton btnOpenFile = new JButton("Открыть файл");
        JButton btnCloseFile = new JButton("Закрыть файл");
        JButton btnCloseWithoutSafe = new JButton("Закрыть без сохранения");
        JButton btnSavePNG = new JButton("Сохранить в PNG");

//        JButton btn5 = new JButton("Кнопка 5");
//        JButton btn6 = new JButton("Кнопка 6");
//        JButton btn7 = new JButton("Кнопка 7");
//        JButton btn8 = new JButton("Кнопка 8");

        leftButtonsPanel.add(btnOpenFile);
        leftButtonsPanel.add(btnCloseFile);
        leftButtonsPanel.add(btnCloseWithoutSafe);
        leftButtonsPanel.add(btnSavePNG);

        // Кнопка справа
        JButton btnCloseApp = new JButton("Выйти");
        btnCloseApp.setBackground(Color.RED);
        btnCloseApp.setForeground(Color.WHITE);

        topPanel.add(leftButtonsPanel, BorderLayout.WEST);
        topPanel.add(btnCloseApp, BorderLayout.EAST);

        // === ЦЕНТРАЛЬНАЯ ОБЛАСТЬ С РАЗДЕЛЕНИЕМ ===
        JSplitPane splitPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        //splitPanel.setDividerLocation(300); // Разделитель на 300 пикселей слева
        splitPanel.setResizeWeight(0.4); // Левая часть получает 40% при изменении размера

        // Левая панель - список фигур
        JPanel panelListOfFigures = new JPanel();
        panelListOfFigures.add(new JLabel("Список фигур"));
        panelListOfFigures.setLayout(new BoxLayout(panelListOfFigures, BoxLayout.Y_AXIS));
        figuresListPanel = panelListOfFigures;

        // Правая панель - для рисования
        MyDrawingPanel panelForDrawing = new MyDrawingPanel();
        panelForDrawing.setBackground(Color.WHITE);
        panelForDrawing.setPreferredSize(new Dimension(1500, 1500));
        drawingPanel = panelForDrawing;

        // Делаем панели прокручиваемыми
        JScrollPane scrollDrawingPanel = new JScrollPane(drawingPanel);
        scrollDrawingPanel.setPreferredSize(new Dimension(600, 600));

        splitPanel.setRightComponent(scrollDrawingPanel);
        splitPanel.setLeftComponent(figuresListPanel);

        // === СБОРКА ВСЕХ КОМПОНЕНТОВ ===
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(splitPanel, BorderLayout.CENTER);

        // Добавляем главную панель в JFrame
        setContentPane(mainPanel);
        setVisible(true);

        // Обработчик для кнопки "Открыть файл"
        btnOpenFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Path filePath = openFileExplorer();
                getFiguresFromFile(filePath);
            }
        });

        // Обработчик для кнопки "Закрыть файл"
        btnCloseFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Path filePath = openFileExplorer();
                closeCurrentFile(filePath);
            }
        });

        // Обработчик для кнопки "Выйти"
        btnCloseApp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Обработчик для кнопки "скриншот"
        btnSavePNG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Path fileName = openFileExplorer();
                SavePhoto(fileName);
            }
        });

        // Обработчик для кнопки "Закрыть без сохранения"
        btnCloseWithoutSafe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                figures.clear();
                setFrameDefault(drawingPanel);
                setFrameDefault(figuresListPanel);
            }
        });
    }


    private void setFrameDefault(JPanel panel){
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
    }


    private void ManageCheckBox(JCheckBox cb){

        try{
            Figure f = (Figure) cb.getClientProperty("figure") ;
            if(cb.isSelected()){
                figures.add(f);
                drawingPanel.repaint();
            }
            else{
                figures.remove(f);
                drawingPanel.repaint();
            }
        }
        catch (Exception e){
            return;
        }
    }
    private final ActionListener myCheckBoxListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JCheckBox cb = (JCheckBox) e.getSource();
            ManageCheckBox(cb);
        }
    };

    protected static ArrayList<Figure> GetFigures(){
        return figures;
    }


    private static class MyDrawingPanel extends JPanel {

        ArrayList<Figure> figures;
        private int prevMouseX, prevMouseY;
        Figure currentFigure;

        @Override
        protected void paintComponent(Graphics g){
            g.clearRect(0,0, g.getClipBounds().width, g.getClipBounds().height);
            this.figures = MyFrame.GetFigures();
            var drawer = new Drawer(g);
           // System.out.println("count of figures " + this.figures.size());
            for (Figure figure : this.figures) {
                figure.Accept(drawer);
            }

        }
        protected MyDrawingPanel(){

            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    handleMousePress(e);
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    handleMouseUp(e);
                }
            });
            addMouseMotionListener(new MouseAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    handleMouseMove(e);
                }
            });
        }
        private void handleMousePress(MouseEvent e){

            int mouseX = e.getX();
            int mouseY = e.getY();

            prevMouseX = mouseX;
            prevMouseY = mouseY;

            this.figures = MyFrame.GetFigures();
            for(int i = figures.size()- 1; i >= 0; i--){
                Figure f = figures.get(i);

                if(f.Contains(mouseX, mouseY)){
                    currentFigure = f;
                    System.out.println("selected");

                    repaint();
                    return;
                }
                else{
                    currentFigure = null;
                }
            }


        }
        private void handleMouseUp(MouseEvent e){
            currentFigure = null;

            repaint();
        }
        private void handleMouseMove(MouseEvent e){
            if(currentFigure != null){
                int deltaX = e.getX() - prevMouseX;
                int deltaY = e.getY() - prevMouseY;

                var drawer = new Drawer(getGraphics());
                currentFigure.Accept(drawer);

                currentFigure.SetShift(deltaX, deltaY);

                currentFigure.Accept(drawer);

                prevMouseX = e.getX();
                prevMouseY = e.getY();

            }
        }
    }
}


