/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.szachy.gui;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mycompany.szachy.Gracz.WykonanyRuch;
import com.mycompany.szachy.pionek.Pionek;
import com.mycompany.szachy.pole.Pole;
import com.mycompany.szachy.pole.Ruch;
import com.mycompany.szachy.pole.Szachownica;
import com.mycompany.szachy.pole.SzachownicaNarzędzia;



import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import javax.swing.JMenuBar;
import java.util.HashSet;
import java.util.*;
import java.util.List;
import javax.imageio.ImageIO;
import java.util.Arrays;

import javax.swing.SwingUtilities.*;
/**
 *
 * @author adamk
 */
public class Tablica
{
    
    public final Color lightTileColor = Color.decode("#FFFACD");
    public final Color darkTileColor = Color.decode("#593E1A");
    public final JFrame gameFrame;
    public final BoardPanel boardPanel;
    public Szachownica szachownica1;
    public static final String defaultPieceImagesPath = "fancy";
    public BoardDirection boardDirection;

    public boolean highlightLegalMoves;

    public Pole sourceTile;
    public Pole destinationTile;
    public Pionek humanMovedPiece;

    public static final Dimension OUTER_FRAME_DIMENSION = new Dimension(600,600);
    public static final Dimension BOARD_PANEL_DIMENSION = new Dimension(400,350);
    public final static Dimension TILE_PANEL_DIMENSION = new Dimension(10,10);
    
    public Tablica()
    {
        this.gameFrame = new JFrame("Jszachy");   
        this.gameFrame.setLayout(new BorderLayout());
        final JMenuBar tableMenuBar = createTableMenuBar();
        this.gameFrame.setJMenuBar(tableMenuBar);
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
        this.szachownica1 = Szachownica.stwórzPlanszę();
        this.gameFrame.setVisible(true);
        this.boardPanel = new BoardPanel();
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
        this.boardDirection =BoardDirection.NORMAL;
        this.highlightLegalMoves = false;
    }

    public JMenuBar createTableMenuBar()
    {
        final JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createFileMenu());
        return tableMenuBar;
    }

    public JMenu createFileMenu()
    {
        final JMenu fileMenu = new JMenu("Plik");
        
        final JMenuItem openPGN = new JMenuItem("Load PGN File");
        openPGN.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("open up that pgn file!");
            }
        });
        fileMenu.add(openPGN);
        
        final JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        });
        fileMenu.add(exitMenuItem);
        
        return fileMenu;

    }

    public JMenu createPreferencesMenu()
        {
            final JMenu preferencesMenu = new JMenu("Preferences");
            final JMenuItem flipBoardMenuItem = new JMenuItem("Flip Board");
            flipBoardMenuItem.addActionListener(new ActionListener()
            {
              @Override
              public void actionPerformed(final ActionEvent e)
              {
                  boardDirection = boardDirection.opposite();
                  boardPanel.rysujSzachownicę(szachownica1);
              }
            });
            preferencesMenu.add(flipBoardMenuItem);
            
            preferencesMenu.addSeparator();
            
            final JCheckBoxMenuItem legalMoveHighlighterCheckbox = new JCheckBoxMenuItem("Highlight legal moves", false);
            
            legalMoveHighlighterCheckbox.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {

                     highlightLegalMoves = legalMoveHighlighterCheckbox.isSelected();

                    
                }
            });
            
            preferencesMenu.add(legalMoveHighlighterCheckbox);
            
            return preferencesMenu;           
        }
        
        public enum BoardDirection
        {
            NORMAL
            {
               @Override
               List<TilePanel> traverse(final List<TilePanel> boardTiles)
               {
                  return boardTiles; 
               }
               
               @Override
               BoardDirection opposite()
               {
                   return FLIPPED;
               }
            },
            FLIPPED
            {
                @Override
                List<TilePanel> traverse(final List<TilePanel> boardTiles)
                {
                    Collections.reverse(boardTiles);
                    return boardTiles;
                }
//                  return ImmutableList.copyOf(Arrays.asList(pola));
                @Override
                BoardDirection opposite()
                {
                    return NORMAL;
                }


            };
            
            abstract List<TilePanel> traverse(final List<TilePanel> boardTiles);
            abstract BoardDirection opposite();
        }

    public class BoardPanel extends JPanel
    {
       final List<TilePanel> boardTiles;
       
       BoardPanel()
       {
           super(new GridLayout(8,8)); //szachownica
           this.boardTiles = new ArrayList<>();
           for(int i =0; i< SzachownicaNarzędzia.NUM_TILES; i++)
           {
               final TilePanel tilePanel = new TilePanel(this, i); //tworzenie każdego pola
               this.boardTiles.add(tilePanel);
               add(tilePanel);
           }
           setPreferredSize(BOARD_PANEL_DIMENSION);
           validate(); //dodaje 64 pól tdo listy ikażde pole jest dodane do panelu szachownicy         
       }
       
       public void rysujSzachownicę(final Szachownica szachownica)
       {
            removeAll();
            for(final TilePanel tilePanel : boardDirection.traverse(boardTiles))
            {
                tilePanel.rysujPole(szachownica);
                add(tilePanel);
            }
            validate();
            repaint();
       }
             
   }

    static publicclass TilePanel extends JPanel
    {
        private final int tileId;
        
        TilePanel(final BoardPanel boardPanel, final int tileId)
        {
            super(new GridBagLayout()); 
            this.tileId = tileId;
            setPreferredSize(TILE_PANEL_DIMENSION);
            assignTileColor();
            assignTilePieceIcon(szachownica1);
            addMouseListener(new MouseListener()
            {

                @Override
                public void mouseClicked(final MouseEvent e)
                {
                   if(isRightMouseButton(e))
                   {
                       sourceTile = null;
                       destinationTile = null;
                       humanMovedPiece = null;
                   }
                    else if (isLeftMouseButton(e))
                       { //drugie kliknięcie
                    
                        if(sourceTile == null)
                        {
                    
                          sourceTile = szachownica1.getPole(tileId);
                          humanMovedPiece = sourceTile.getPionek();
                          if (humanMovedPiece == null)
                          {
                              sourceTile = null;
                          }
                        }
                        else
                        {
                            destinationTile = szachownica1.getPole(tileId);
                            final Ruch ruch = Ruch.RuchFabryka.stwórzRuch(szachownica1, sourceTile.getPoleWspół(), destinationTile.getPoleWspół());
                            final WykonanyRuch transition = szachownica1.obecnyGracz().wykonajRuch(ruch);
                            if (transition.getStatusRuchu().isWykonany())
                            {
                                szachownica1 = transition.getSzachownica();
                            }
                            sourceTile = null;
                            destinationTile = null;
                            humanMovedPiece = null;
                        }
                        SwingUtilities.invokeLater(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                boardPanel.rysujSzachownicę(szachownica1);
                            }
                        });
                       
                    }
                }

                @Override
                public void mousePressed(final MouseEvent e)
                {

                }

                @Override
                public void mouseReleased(final MouseEvent e)
                {
            
                }

                @Override
                public void mouseEntered(final MouseEvent e) 
                {
                   
                }
                @Override
                public void mouseExited(final MouseEvent e) 
                {
          
                }

//                private boolean isRightMouseButton(MouseEvent anEvent) 
//                {
//                    return ((anEvent.getModifiersEx() & InputEvent.BUTTON3_DOWN_MASK != 0 ||
//                            anEvent.getButton()==MouseEvent.BUTTON3));
//                }
            });
            validate();
        }

        public void highlightLegals(final Szachownica szachownica)
        {
            if (highlightLegalMoves)
            {
                for(final Ruch ruch : pionekLegalneRuchy(szachownica))
                {
                    if(ruch.getCel() == this.tileId)
                    { 
                      try
                      {
                          add(new JLabel(new ImageIcon(ImageIO.read(new File("fancy/greem_dot.png")))));
                      } catch(Exception e)
                      {
                          e.printStackTrace();
                      }
                    }
                }
            }
        }

        publicCollection<Ruch> pionekLegalneRuchy(final Szachownica szachownica)
        {
            if(humanMovedPiece != null && humanMovedPiece.getpionekKolor() == szachownica.obecnyGracz().getKolor())
            {
               return humanMovedPiece.calculateLegalMoves(szachownica);
            }
            return Collections.emptyList();
        }
        
        public void rysujPole(final Szachownica szachownica)
        {
            assignTileColor();
            assignTilePieceIcon(szachownica);
            validate();
            repaint();
        }

        public void assignTilePieceIcon(final Szachownica szachownica)
        {
           this.removeAll();
           if(szachownica.getPole(this.tileId).PoleInfo())
           {
               try 
               {
                   
                    final BufferedImage image = ImageIO.read(new File(defaultPieceImagesPath + szachownica.getPole(this.tileId).getPionek().getpionekKolor().toString().substring(0, 1) +
                            szachownica.getPole(PROPERTIES).getPionek().toString() + ".gif"));
                    add(new JLabel(new ImageIcon()));
               } 
               catch (IOException e)
                {
                    e.printStackTrace();
                }
               
               }
        }

        public void assignTileColor()
        {
            if(SzachownicaNarzędzia.EIGHTH_RANK[this.tileId] ||
                   SzachownicaNarzędzia.SIXTH_RANK[this.tileId] ||
                   SzachownicaNarzędzia.FOURTH_RANK[this.tileId] ||
                   SzachownicaNarzędzia.SECOND_RANK[this.tileId]
//                   SzachownicaNarzędzia.SECOND_RANK[this.tileId]
                    )
            {
               setBackground(this.tileId % 2 == 0 ? lightTileColor : darkTileColor); 
            }
            else if (SzachownicaNarzędzia.SEVENTH_RANK[this.tileId] ||
                    SzachownicaNarzędzia.FIFTH_RANK[this.tileId] ||
                    SzachownicaNarzędzia.THIRD_RANK[this.tileId] ||
                    SzachownicaNarzędzia.FIRST_RANK[this.tileId])
            {
                setBackground(this.tileId % 2 != 0 ? lightTileColor : darkTileColor); 
            }

        } 
        
  public static boolean isRightMouseButton(MouseEvent anEvent) 
  {
  return checkMouseButton(anEvent, MouseEvent.BUTTON3,
                                InputEvent.BUTTON3_DOWN_MASK);
  }

  public static boolean isLeftMouseButton(MouseEvent anEvent) 
  {
        return checkMouseButton(anEvent, MouseEvent.BUTTON1,
                                InputEvent.BUTTON1_DOWN_MASK);
  }
  
        private static boolean checkMouseButton(MouseEvent anEvent,
                                                  int mouseButton,
                                                  int mouseButtonDownMask)
          {
              switch (anEvent.getID()) 
              {
              case MouseEvent.MOUSE_PRESSED:
              case MouseEvent.MOUSE_RELEASED:
              case MouseEvent.MOUSE_CLICKED:
                  return (anEvent.getButton() == mouseButton);

              case MouseEvent.MOUSE_ENTERED:
              case MouseEvent.MOUSE_EXITED:
              case MouseEvent.MOUSE_DRAGGED:
                  return ((anEvent.getModifiersEx() & mouseButtonDownMask) != 0);

              default:
                  return ((anEvent.getModifiersEx() & mouseButtonDownMask) != 0 ||
                          anEvent.getButton() == mouseButton);
              }
          }
    }
    
    
            
    

}
