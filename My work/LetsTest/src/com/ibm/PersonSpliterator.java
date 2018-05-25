package com.ibm;

import java.util.Spliterator;
import java.util.function.Consumer;

public class PersonSpliterator implements Spliterator<Person> {
	
	
	private Spliterator<String> lineSpliterator;
	private String address;
	private int age;
	private String name;
	
	public PersonSpliterator(Spliterator<String> lineSpliterator) {
		
		this.lineSpliterator = lineSpliterator;
	}

	@Override
	public int characteristics() {
		return lineSpliterator.characteristics();
	}
	
	//RTC , Jazz sandbox

	@Override
	public long estimateSize() {
		return lineSpliterator.estimateSize() / 3;
	}
	@Override
	public boolean tryAdvance(Consumer<? super Person> action) {
		
		if(this.lineSpliterator.tryAdvance(line -> this.name=line) &&
				
				this.lineSpliterator.tryAdvance(line -> this.age=Integer.parseInt(line)) &&
				
				this.lineSpliterator.tryAdvance(line -> this.address=line))
		{
			Person p = new Person();
			p.setAddress(this.address);
			p.setAge(this.age);
			p.setName(this.name);
			action.accept(p);
			return true;
		}
		else
		{
			return false;
		}
		
	}

	@Override
	public Spliterator<Person> trySplit() {
		return null;
	}

}
