package musicInstruments;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

import musicInstruments.Instrument.InstrumentType;

public class Shop {

	private static final double MARKUP = 2.5;

	enum Sort {
		ASCENDING, DESCENDING;
	}
	
	private String name;
	private double money;
	private double income = 0;
	private TreeMap<InstrumentType, HashMap<String, Instrument>> instruments;
	private HashMap<Instrument, Integer> sales;
	
	Shop(String name) {
		this.name = name;
		instruments = new TreeMap<>();
		instruments.put(InstrumentType.PERCUSSION, new HashMap<>());
		Instrument i1 = new Drums("Perlite", 1500, 3);
		instruments.get(InstrumentType.PERCUSSION).put("Pearls", i1);
		instruments.get(InstrumentType.PERCUSSION).put("Yamaha", new Drums("Yamaha Drums", 1850, 2));
		instruments.put(InstrumentType.KEYBOARDS, new HashMap<>());
		instruments.get(InstrumentType.KEYBOARDS).put("Yamaha", new Piano("Yamaha Piano", 2100, 2));
		instruments.get(InstrumentType.KEYBOARDS).put("Lassy", new Piano("Lassy", 59, 9));
		instruments.put(InstrumentType.STRING_INSTRUMENT, new HashMap<>());
		instruments.get(InstrumentType.STRING_INSTRUMENT).put("Fender", new Guitar("Fender", 800, 8));
		instruments.get(InstrumentType.STRING_INSTRUMENT).put("Orfei", new Guitar("Orfei", 20, 200));
		sales = new HashMap<>();
	}
	
	public void sell(String name, int quantity) {
		for(InstrumentType type : instruments.keySet()) {
			if(instruments.get(type).containsKey(name)) {
				Instrument i = instruments.get(type).get(name);
				if(i.getQuantity() < quantity){
					System.out.println("Not enough quantity, only " + i.getQuantity() + " left");
				}
				else{
					i.decreaseQuantity(quantity);
					System.out.println("Sold!");
					this.money += quantity*i.getPrice()*MARKUP;
					this.income += quantity*i.getPrice()*MARKUP;
					if(sales.containsKey(i)) {
						sales.put(i, sales.get(i) + quantity);
					}
					else {
						sales.put(i, quantity);
					}
					break;
				}
			}
		}
	}
	
	public void receive(String name, int quantity) {
		for(InstrumentType type : instruments.keySet()) {
			if(instruments.get(type).containsKey(name)) {
				Instrument i = instruments.get(type).get(name);
				if(this.money >= quantity*i.getPrice()) {
					i.increaseQuantity(quantity);
					System.out.println("Added!");
					this.money -= quantity*i.getPrice();
				}
				else {
					System.out.println("Not enough money");
				}
			}
		}
	}
	
	
	public void printInstrumentsByType() {
		for(InstrumentType type : instruments.keySet()){
			System.out.println("========================="+ type +"========================");
			for(Instrument i : instruments.get(type).values()){
				System.out.println(i);
			}
		}
	}
	
	public void printInstrumentsByName() {
		printInstruments(null);
	}
	
	public void printInstrumentsByPrice(Sort s) {
		
		printInstruments((i1, i2) -> {
			return Double.compare(i1.getPrice(), i2.getPrice()) * (s == Sort.ASCENDING ? 1 : -1); 
		} );
		
	}
	
	private void printInstruments(Comparator<Instrument> comp) {
		TreeSet<Instrument> result = comp == null ? new TreeSet<>() : new TreeSet<>(comp);
		for(HashMap<String, Instrument> h : instruments.values()) {
			result.addAll(h.values());
		}	
		for(Instrument i : result) {
			System.out.println(i);
		}
	}
	
	public double getIncome() {
		return income;
	}
	
	public double getMoney() {
		return money;
	}
	
	public void printAvailableInstruments() {
		for(InstrumentType type : instruments.keySet()) {
			System.out.println("========================="+ type +"=======================");
			for(Instrument i : instruments.get(type).values()){
				if(i.getQuantity() > 0) {
					System.out.println(i);
				}
			}
		}
	}
	
	public void printSales() {
		ArrayList<Entry<Instrument, Integer>> list = new ArrayList<>();
		list.addAll(sales.entrySet());
		Collections.sort(list, new Comparator<Entry<Instrument, Integer>>() {
			@Override
			public int compare(Entry<Instrument, Integer> o1, Entry<Instrument, Integer> o2) {
				return o2.getValue() - o1.getValue();
			}
		});
		for(Entry<Instrument, Integer> e : list) {
			System.out.println(e.getKey());
		}

	}

	public void printMostSold() {
		Instrument maxSold = null;
		int maxSale = 0;
		Instrument minSold = null;
		int minSale = 0;
		for(Entry<Instrument, Integer> e : sales.entrySet()) {
			if(maxSold == null) {
				maxSold = e.getKey();
				maxSale = e.getValue();
				minSold = e.getKey();
				minSale = e.getValue();
			}
			else {
				if(e.getValue() > maxSale) {
					maxSale = e.getValue();
					maxSold = e.getKey();
				}
				if(e.getValue() < minSale) {
					minSale = e.getValue();
					minSold = e.getKey();
				}
			}
		}
		System.out.println("Max sale =" + maxSold);
		System.out.println("Min sale =" + minSold);
		
	}
	
	public void printMostSoldType() {
		int maxCount = 0;
		InstrumentType maxType = null;
		for(InstrumentType type : instruments.keySet()) {
			int countSales = 0; //sales for this type
			for(Instrument i : instruments.get(type).values()) {
				countSales += sales.get(i) == null ? 0 : sales.get(i);	
			}
			if(countSales > maxCount) {
				maxType = type;
				maxCount = countSales;
			}
		}
		System.out.println("Max sales for type = " + maxType);
	}
	
	public void printMostProfitableType() {
		double maxProfit = 0;
		InstrumentType maxType = null;
		for(InstrumentType type : instruments.keySet()){
			double countProfit = 0; 
			for(Instrument i : instruments.get(type).values()) {
				countProfit += sales.get(i) == null ? 0 : sales.get(i)*i.getPrice()*MARKUP;	
			}
			if(countProfit > maxProfit) {
				maxType = type;
				maxProfit = countProfit;
			}
		}
		System.out.println("Most profitable type = " + maxType);
	}
	
}

