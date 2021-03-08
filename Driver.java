import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Driver {

	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
		Functions func=new Functions();
		Scanner sc=new Scanner(System.in);
		File f=new File("C:\\Users\\Mohit\\eclipse-workspace\\Ass09\\src\\movieDetails.txt");
		List<Movie> m=func.populateMovies(f);
		System.out.println("Enter your choice");
		int choice=sc.nextInt();
		do {
			switch (choice) {
			case 1:
				System.out.println(func.populateMovies(f)); 
				break;
			case 2:
				func.allMoviesInDb(m);
				break;
			case 3:
				ArrayList< String> casting=new ArrayList<>();
				casting.add("ActorC");
				casting.add("ActorD");
				Movie movie=new Movie(8, "XYZ", "Y","Hindi", Date.valueOf("2005-06-16"), casting, 4.2, 2500000);
				func.addMovie(movie, m);
				func.allMoviesInDb(m);
				System.out.println(m);
				break;
			case 4:
				func.serializeMovies(m, "MovieSer");
				break;
			case 5:
				System.out.println(func.deserializeMovie("MovieSer"));
				break;
			case 6:
				System.out.println("Enter Year");
				int year=sc.nextInt();
				System.out.println(func.getMoviesReleasedInYear(year));
				break;
			case 7:
				System.out.println("Enter actor name");
				String actor=sc.next();
				System.out.println(func.getMoviesByActor(actor));
				break;
			case 8:
				func.updateRatings(m.get(1), 2.9, m);
				func.allMoviesInDb(m);                                  
				System.out.println(m);
				break;
			case 9:
				func.updateBusiness(m.get(0), 15000, m);
				func.allMoviesInDb(m);  
				System.out.println(m);
				break;
			case 10:
				System.out.println(func.businessDone(15000));
				break;
			default:
				break;
			}
			System.out.println("Enter choice -");
			choice=sc.nextInt();
		}while(choice!=12);
		
	}

}
