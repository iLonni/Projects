package musicInstruments;

public class Guitar extends Instrument {

	public Guitar(String name, double price, int quantity) {
		super(name, price, quantity, InstrumentType.STRING_INSTRUMENT);
	}
	
}
