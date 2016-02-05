package sicxe.model.simulator.assembler.listing;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by maciek on 23/01/16.
 */
public class ProgramListing{

    private List<ListingLine> listingLines;

    public ProgramListing() {
        listingLines = new ArrayList<>();
    }

    public boolean add(ListingLine line){
        return listingLines.add(line);
    }

    public ListingLine get(int i){
        return listingLines.get(i);
    }

    public int size(){
        return listingLines.size();
    }

    public List<ListingLine> getListingLines() {
        return listingLines;
    }

    public void setListingLines(List<ListingLine> listingLines) {
        this.listingLines = listingLines;
    }
}
