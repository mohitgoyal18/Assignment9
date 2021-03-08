import java.util.Comparator;

public class MovComparator implements Comparator<Movie>{

	@Override
	public int compare(Movie o1, Movie o2) {
		if(o1.getTotalBusinessDone()>o2.getTotalBusinessDone())
		return -1;
		else if(o1.getTotalBusinessDone()>o2.getTotalBusinessDone())
			return +1;
		else 
			return 0;
	}

}
