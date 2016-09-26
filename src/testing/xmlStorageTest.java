package testing;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import core.Location;
import gameobjects.Tree;
import tile.Tile;
import tile.WaterTile;
import util.Position;

public class xmlStorageTest {

	public static void main(String[] args) {
		Tile[][] tiles = new Tile[2][2];
		for (int x = 0; x < 2; x++) {
			for (int y = 0; y < 2; y++) {
				tiles[x][y] = new WaterTile(new Position(x, y), new Tree());
			}
		}

		Location l = new Location("CheeseFactory", tiles);
		try {
			marshExample(l);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void marshExample(Location l) throws Exception {
		JAXBContext jaxbcontext = JAXBContext.newInstance(Location.class);
		Marshaller marshaller = jaxbcontext.createMarshaller();

		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		marshaller.marshal(l, new File("cheese.xml"));

		Unmarshaller jaxbUnmarshaller = jaxbcontext.createUnmarshaller();
		Location p = (Location) jaxbUnmarshaller.unmarshal(new File("cheese.xml"));
		
		jaxbcontext = JAXBContext.newInstance(Location.class);
		marshaller = jaxbcontext.createMarshaller();

		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		marshaller.marshal(p, System.out);
	}
}
