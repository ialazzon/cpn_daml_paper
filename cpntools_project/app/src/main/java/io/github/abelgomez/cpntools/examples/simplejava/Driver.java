package io.github.abelgomez.cpntools.examples.simplejava;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import io.github.abelgomez.cpntools.Cpnet;
import io.github.abelgomez.cpntools.io.serializer.CpnToolsBuilder;
import io.github.abelgomez.cpntools.io.serializer.SerializationException;

public class Driver {
	public static void main(String args[]) throws SerializationException, IOException{		
		Cpnet net = ExampleCPN.buildSampleNet();
		CpnToolsBuilder builder = new CpnToolsBuilder(net);
		builder.serialize(new FileOutputStream(new File(args[0])));
		
	}
}
