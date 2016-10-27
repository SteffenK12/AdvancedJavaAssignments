import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Steffen Kienzler on 26.10.2016.
 */

/**
 * Tool to read multi FastA files and extract featrues regarding the length of the Sequences and count of Nucleotides.
 * Results will be printed in a fornatted output.
 */
public class FastaTool {
    private String file_path;
    private ArrayList<Sequence> sequences;
    private ArrayList<String> headers;

    FastaTool(String file_path) {
        this.file_path = file_path;
        this.sequences = new ArrayList<Sequence>();
        this.headers = new ArrayList<>();
    }

    /**
     * Multi FastA filereader storing the Sequences in Sequence Objects and Headers as Strings.
     * @throws IOException
     */
    void readFasta() throws IOException {
        BufferedReader bufRead = new BufferedReader(new FileReader(file_path));
        String lines = "";
        for (String line = bufRead.readLine(); line != null; line = bufRead.readLine()) {
            if (line.startsWith(">")) {
                if(!lines.isEmpty()) {
                    sequences.add(new Sequence(lines));
                    lines="";
                }
                headers.add(line.replace(">",""));
            } else {
                lines += line;
            }
        }
        sequences.add(new Sequence(lines));
        bufRead.close();
    }

    /**
     * Counting all Nucleotides and the number of gaps within all sequences together.
     * @return int array of number of nucleotides and gaps
     */
    private int[] num_nucleotides() {
            int num_A = 0, num_G = 0, num_C = 0, num_U = 0, num_gaps = 0;
            for (Sequence n : sequences) {
                num_A += n.getNumA();
                num_G += n.getNumG();
                num_C += n.getNumC();
                num_U += n.getNumU();
                num_gaps += n.getNumGaps();
            }
            return new int[]{num_A, num_C, num_G, num_U, num_gaps};
        }

    /**
     * Shortest length of all sequences in the FastA file.
     * @return int array of shortest length of the sequences with and without gaps
     */
    private int[] shortestLength(){
        int min = Integer.MAX_VALUE, min_no_gaps = Integer.MAX_VALUE;
        for(Sequence n : sequences){
            if(n.getTotalLength() < min){
                min = n.getTotalLength();
            }
            if(n.getLengthNoGaps() < min_no_gaps){
                min_no_gaps = n.getLengthNoGaps();
            }
        }
        return new int[]{min,min_no_gaps};
    }

    /**
     * Longest length of all sequences in the FastA file.
     * @return int array of longest length of the sequences with and without gaps
     */
    private int[] longestLength(){
        int max = 0, max_no_gaps = 0;
        for(Sequence n : sequences){
            if(n.getTotalLength() > max){
                max = n.getTotalLength();
            }
            if(n.getLengthNoGaps() > max_no_gaps){
                max_no_gaps = n.getLengthNoGaps();
            }
        }
        return new int[]{max,max_no_gaps};
    }

    /**
     * Average length of all sequences in the FastA file.
     * @return int array of average length of the sequences with and without gaps
     */
    private int[] averageLength(){
        int sum = 0, sum_no_gaps = 0, average = 0, average_no_gaps = 0;
            for(Sequence n : sequences){
                sum += n.getTotalLength();
                sum_no_gaps += n.getLengthNoGaps();
            }
        average = Math.round(sum/headers.size());
        average_no_gaps = Math.round(sum_no_gaps/headers.size());
        return new int[]{average,average_no_gaps};
    }

    /**
     * Formating and printing the output of the given FastA file.
     */
    void printFormated() {
        int[] count = num_nucleotides();
        System.out.println(String.format("%31s %60s", 1, (sequences.get(0).getSequence().length()/2)+1));
        for (int i = 0; i < headers.size(); i++) {
            System.out.println(String.format("%-30s", headers.get(i)) + sequences.get(i).getSequence().substring(0, (sequences.get(0).getSequence().length()/2)+1));
        }

        System.out.println();
        System.out.println(String.format("%31s %59s", (sequences.get(0).getSequence().length()/2)+2, sequences.get(0).getSequence().length()));
        for (int i = 0; i < headers.size(); i++) {
            System.out.println(String.format("%-30s", headers.get(i)) + sequences.get(i).getSequence().substring(60, sequences.get(i).getSequence().length()));
        }

        System.out.println();
        System.out.println("Number of Sequences: " + headers.size());
        System.out.println(String.format("%-21s", "Shortest length: ") + shortestLength()[0] + " (excluding '-'s: " + shortestLength()[1] + ")");
        System.out.println(String.format("%-21s", "Average length: ") + averageLength()[0] + " (excluding '-'s: " + averageLength()[1] + ")");
        System.out.println(String.format("%-21s", "Longest length: ") + longestLength()[0] + " (excluding '-'s: " + longestLength()[1] + ")");
        System.out.print("Counts: A: " + count[0] + ", C: " + count[1] + ", G: " + count[2] + ", U: " + count[3] + ", -: " + count[4]);
    }
}

