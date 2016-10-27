/**
 *  Created by Steffen Kienzler on 26.10.2016.
*/

 /**
 * Class containing the representation of a sequence as well as further information about the nucleotide number and
  * length of the sequence.
 */
public class Sequence {
    private Nucleotide[] Nucleotides;
    private String sequence;
    private int length_no_gaps;
    private int total_length;
    private int num_A,num_G,num_C,num_U,num_gaps;

     /**
      * Constructs array of nucleotide objects from the sequence string and also counting the nucleotides in the sequence.
      * @param _sequence sequence to be worked on as string.
      */
    public Sequence(String _sequence) {
        sequence = _sequence;
        Nucleotides = new Nucleotide[sequence.length()];

        this.length_no_gaps = 0;
        this.total_length = 0;
        this.num_A = 0;
        this.num_G = 0;
        this.num_C = 0;
        this.num_U = 0;
        this.num_gaps = 0;

        for (int i = 0; i < sequence.length(); i++) {
            char c = sequence.charAt(i);
            Nucleotide n = new Nucleotide(c);
            Nucleotides[i] = n;
            total_length += 1;
            if(n.getBase() == 'A') {
                num_A += 1;
            }
            if(n.getBase() == 'G') {
                num_G += 1;
            }
            if(n.getBase() == 'C') {
                num_C += 1;
            }
            if(n.getBase() == 'U') {
                num_U += 1;
            }
            if(n.getBase() == '-') {
                num_gaps += 1;
            }
            if(n.getBase() != '-') {
                length_no_gaps += 1;
            }
        }
    }


    int getTotalLength(){
        return total_length;
    }
    int getLengthNoGaps(){
        return length_no_gaps;
    }
    int getNumA(){
        return num_A;
    }
    int getNumG(){
        return num_G;
    }
    int getNumC(){
        return num_C;
    }
    int getNumU(){
        return num_U;
    }
    int getNumGaps(){
        return num_gaps;
    }
    public String getSequence(){
        return sequence;
    }

}

