package musicInstruments;

import musicInstruments.Shop.Sort;

public class Demo {

	public static void main(String[] args) {
		Shop s = new Shop("Talents music stuff");
		s.printInstrumentsByType();
		s.sell("Orpheus", 13);
		s.printInstrumentsByType();
		s.receive("Fender", 2);
		s.printInstrumentsByType();
		System.out.println(s.getMoney());
		s.printInstrumentsByPrice(Sort.ASCENDING);
		System.out.println();
		s.printInstrumentsByPrice(Sort.DESCENDING);
		System.out.println();
		s.printAvailableInstruments();
		System.out.println();
		s.printSales();
		System.out.println(s.getIncome());
		System.out.println();
		s.printMostSold();
		System.out.println();
		s.printMostSoldType();
		System.out.println();
		s.printMostProfitableType();
	}
}
