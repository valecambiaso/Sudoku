package GUI;
import javax.swing.JLabel;


import javax.swing.JList;
import javax.swing.ListCellRenderer;

import java.awt.Component;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

/**
 * Clase que modela y actualiza las imagenes del JComboBox.
 *
 */
@SuppressWarnings("serial")
public class RenderJComboBox extends JLabel implements ListCellRenderer<Object> {
	Map<Integer,ImageIcon> icono = null; //Por cada entero quiero almacenar un ícono.
	private String[] imagenes;

	/**
	 * Mapeo cada entero con una imagen.
	 */
    public RenderJComboBox() {
        icono = new HashMap<Integer, ImageIcon>();
        this.imagenes = new String[] {"/imgbn/nulo.png","/imgbn/bananas-bn.png","/imgbn/broccoli-bn.png","/imgbn/carrot-bn.png","/imgbn/coffee-cup-bn.png","/imgbn/cupcake-bn.png","/imgbn/donut-bn.png","/imgbn/hamburger-bn.png","/imgbn/pizza-bn.png","/imgbn/taco-bn.png"};
        for(int i = 0;i<imagenes.length;i++) {
        	ImageIcon imgIcon = new ImageIcon(this.getClass().getResource(this.imagenes[i]));
        	Image image = imgIcon.getImage();
        	Image newimg = image.getScaledInstance(50,50,java.awt.Image.SCALE_SMOOTH);
			imgIcon.setImage(newimg);
        	icono.put(i,imgIcon);
        }
    }


    @Override
    public Component getListCellRendererComponent(@SuppressWarnings("rawtypes") JList list, Object value,int index, boolean isSelected, boolean cellHasFocus) {
    	if (icono.get(value) != null)
        	setIcon(icono.get(value));
    	else
            setIcon(null);
    	return this;
    }
}
