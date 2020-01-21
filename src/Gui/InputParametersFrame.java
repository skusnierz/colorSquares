import java.awt.event.*;
import javax.swing.*;
import javax.swing.JTextField;
import java.sql.Time;
import java.util.concurrent.atomic.AtomicBoolean;

public class InputParametersFrame extends JPanel{

    private int numberOfOpponents = 1;
    private JPanel inputParametersFrame = new JPanel();
    private BoardSquares boardSquares;
    ColorSquaresBoard colorSquaresBoard;
    Timer timer = new Timer(1, null);

    InputParametersFrame() {
        JTextField widthTextField = new JTextField();
        JLabel widthLabel = new JLabel();
        JButton submitButton = new JButton("Submit");
        JLabel heightLabel = new JLabel();
        JTextField heightTextField = new JTextField();
        JLabel numberOfOpponentsLabel = new JLabel();
        JRadioButton oneOpponentButton = new JRadioButton("1", true);
        JRadioButton twoOpponentsButton = new JRadioButton("2");
        JRadioButton threeOpponentsButton = new JRadioButton("3");

        // Submit Button
        submitButton.setBounds(100,250,140, 40);

        // Width Input Text
        widthLabel.setText("Width : ");
        widthLabel.setBounds(10, 10, 100, 110);
        widthTextField.setText("20");
        widthTextField.setBounds(110, 50, 130, 30);

        // Height Input Text
        heightLabel.setText("Height : ");
        heightLabel.setBounds(10, 70, 100, 110);
        heightTextField.setText("20");
        heightTextField.setBounds(110, 110, 130, 30);

        // Number Of Opponents Buttons Group
        numberOfOpponentsLabel.setText("Number Of Opponents :");
        numberOfOpponentsLabel.setBounds(10, 120, 200, 100);
        oneOpponentButton.setBounds(210, 145, 50, 50);
        twoOpponentsButton.setBounds(260, 145, 50, 50);
        threeOpponentsButton.setBounds(310, 145, 50, 50);

        // Frame Configuration
        inputParametersFrame.add(numberOfOpponentsLabel);
        inputParametersFrame.add(oneOpponentButton);
        inputParametersFrame.add(twoOpponentsButton);
        inputParametersFrame.add(threeOpponentsButton);
        inputParametersFrame.add(widthTextField);
        inputParametersFrame.add(widthLabel);
        inputParametersFrame.add(heightLabel);
        inputParametersFrame.add(heightTextField);
        inputParametersFrame.add(submitButton);
        inputParametersFrame.setSize(350,450);
        inputParametersFrame.setLayout(null);
        inputParametersFrame.setVisible(true);

        oneOpponentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                twoOpponentsButton.setSelected(false);
                threeOpponentsButton.setSelected(false);
                numberOfOpponents = 1;
            }
        });

        twoOpponentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                oneOpponentButton.setSelected(false);
                threeOpponentsButton.setSelected(false);
                numberOfOpponents = 2;
            }
        });

        threeOpponentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                oneOpponentButton.setSelected(false);
                twoOpponentsButton.setSelected(false);
                numberOfOpponents = 3;
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                // Create Squares Board

                JFrame colorSquareBoard = new JFrame("Color Squares Board");
                colorSquareBoard.setSize(800,800);
                colorSquareBoard.setResizable(false);

                JRadioButton mapCreateMode = new JRadioButton("Create your map yourself !");
                mapCreateMode.setBounds(20, 20, 250, 50);

                JButton clearMap = new JButton("Clear Map");
                clearMap.setBounds(270, 30, 150,30);

                JButton drawObstaclesButton = new JButton("Draw Obstacles");
                drawObstaclesButton.setBounds(430, 30, 150,30);

                JButton drawOpponentsButton = new JButton("Draw Opponents");
                drawOpponentsButton.setBounds(590, 30, 180,30);

                JButton startButton = new JButton("Start");
                startButton.setBounds(310, 720, 180,30);



                int numberOfWidthSquares = Integer.parseInt(widthTextField.getText());
                int numberOfHeightSquares = Integer.parseInt(heightTextField.getText());
                boardSquares = new BoardSquares(numberOfOpponents, numberOfHeightSquares, numberOfWidthSquares);
                boardSquares.addObstaclesPosition();
                boardSquares.addOpponents();
                colorSquaresBoard = new ColorSquaresBoard( numberOfWidthSquares, numberOfHeightSquares, boardSquares);
                colorSquaresBoard.setLocation(600,600);

                colorSquareBoard.add(mapCreateMode);
                colorSquareBoard.add(startButton);
                colorSquareBoard.add(clearMap);
                colorSquareBoard.add(drawObstaclesButton);
                colorSquareBoard.add(drawOpponentsButton);
                colorSquareBoard.add(colorSquaresBoard);
                colorSquareBoard.setLocationRelativeTo(null);
                colorSquareBoard.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                colorSquareBoard.setVisible(true);


                colorSquareBoard.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        int yCord = ((e.getX()-100)/colorSquaresBoard.squareWidth);
                        int xCord = ((e.getY()-140)/colorSquaresBoard.squareHeight);
                        if(xCord < 0 || yCord < 0 || yCord > numberOfWidthSquares-1 || xCord > numberOfHeightSquares-1) return;
                        Vector2D vec = new Vector2D(xCord, yCord);
                        AtomicBoolean freePosition = new AtomicBoolean(true);
                        if(mapCreateMode.isSelected()) {
                            boardSquares.gamers.forEach(gamer -> {
                                if(gamer.positions.contains(vec)) return;
                            });
                            boardSquares.obstaclePositions.removeIf(pos -> {
                                if(pos.getX() == vec.getX() && pos.getY() == vec.getY()) {
                                    freePosition.set(false);
                                    return true;
                                }
                                return false;
                            });
                            if(freePosition.get()) boardSquares.addNewObstaclePoint(vec);
                        } else {
                            boardSquares.addHumanGamer(vec);
                        }
                        colorSquareBoard.repaint();
                    }
                });

                clearMap.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        boardSquares.clearObstacles();
                        boardSquares.clearGamers();
                        colorSquareBoard.repaint();
                    }
                });

                drawObstaclesButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        boardSquares.clearObstacles();
                        boardSquares.addObstaclesPosition();
                        colorSquareBoard.repaint();
                    }
                });

                drawOpponentsButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        boardSquares.clearGamers();
                        boardSquares.addOpponents();
                        colorSquareBoard.repaint();
                    }
                });


                ActionListener startMap = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        if(boardSquares.humanGamer == null) {
                            JOptionPane.showMessageDialog(colorSquareBoard,
                                    "You don't place your player on board!!!",
                                    "Warning!!",
                                    JOptionPane.WARNING_MESSAGE);
                            timer.stop();
                            return;
                        }

                        if(boardSquares.nextTurn() == 0 ) {
                            timer.stop();
                            GameResultFrame gameResultFrame = new GameResultFrame(boardSquares.getScores());
                            gameResultFrame.tryAgainButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent arg0) {
                                    boardSquares.tryAgain();
                                    gameResultFrame.exit();
                                    colorSquareBoard.repaint();
                                }
                            });
                        }
                        colorSquareBoard.repaint();
                    }
                };

                timer.addActionListener(startMap);
                timer.setDelay(200);

                startButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        timer.start();
                    }
                });
            }
        });
    }

    JPanel getInputJPanel() {
        return this.inputParametersFrame;
    }
}

