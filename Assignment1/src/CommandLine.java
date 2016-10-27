import java.io.IOException;

/**
 * Created by Steffen Kienzler on 26.10.2016.
 */

/**
 * Provides the program to run by CMD, the first argument has to be the specific path to a FastA file.
 */
public class CommandLine {
    public static void main(String[] args) throws IOException {
        String file = args[0];
        FastaTool fasta_reader = new FastaTool(file);
        fasta_reader.readFasta();
        fasta_reader.printFormated();
    }
}
