package model.labelprovider;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import model.Person;
import view.MyTableViewer;

public class MarriedLabelProvider extends ColumnLabelProvider {
   private static final Image  CHECKED   = getImage("checked.gif");
   private static final Image  UNCHECKED = getImage("unchecked.gif");

   @Override
   public String getText(Object element) {
      return null;
   }

   @Override
   public Image getImage(Object element) {
      if (((Person) element).isMarried()) {
         return CHECKED;
      } else {
         return UNCHECKED;
      }
   }

   private static Image getImage(String file) {
      Bundle bundle = FrameworkUtil.getBundle(MyTableViewer.class);
      URL url = FileLocator.find(bundle, new Path("icons/" + file), null);
      ImageDescriptor image = ImageDescriptor.createFromURL(url);
      return image.createImage();
   }
}
