import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Functions {
	
	List<Movie> populateMovies(File file)
	{
		List<Movie> list=new ArrayList<Movie>();
		try {
			Scanner reader=new Scanner(file);	
			while(reader.hasNext())
			{
				String[] temp=reader.next().split(",");
				List<String> casting=new ArrayList<String>();
				for(int i=5;i<temp.length-2;i++)
				{
					casting.add(temp[i]);
				}
				Movie movie=new Movie(Integer.parseInt(temp[0]), temp[1], temp[2], temp[3], Date.valueOf(temp[4]), casting, Double.parseDouble(temp[temp.length-2]),Double.parseDouble( temp[temp.length-1]));
			   list.add(movie);
			   }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	boolean allMoviesInDb(List<Movie> movies) throws SQLException {
		Connection con=DbConnection.getConnection();
		Statement stmt=con.createStatement();
		try {		
		for(Movie m:movies) 
		{
			String concat="";
			List<String> temp=m.getCasting();
			for(String s:temp)
				concat=concat+s+",";
			concat=concat.trim();
		PreparedStatement pstmt=con.prepareStatement("insert into movie values(?,?,?,?,?,?,?,?)");
		pstmt.setInt(1, m.getMovieId());
		pstmt.setString(2, m.getMovieName());
		pstmt.setString(3, m.getMovieName());
		pstmt.setString(4, m.getMovieType());
		pstmt.setDate(5, m.getReleaseDate());
		pstmt.setString(6,concat);
		pstmt.setDouble(7, m.getRating());
		pstmt.setDouble(8, m.getTotalBusinessDone());
		pstmt.execute();
		}
		}catch (Exception e) {
			con.close();
			return false;
		}
		con.close();
		return true;
	}

	void addMovie(Movie movie,List<Movie> movies)
	{
		movies.add(movie);
	}
	
	void serializeMovies(List<Movie> movies, String fileName) throws IOException
	{
		File f=new File(fileName);
		if(!f.exists())
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		FileOutputStream fs=new FileOutputStream(f);
		ObjectOutputStream os=new ObjectOutputStream(fs);
		os.writeObject(movies);
	}
	List<Movie> deserializeMovie(String filename) throws IOException, ClassNotFoundException
	{
		File f=new File(filename);
		FileInputStream fis=new FileInputStream(filename);
		ObjectInputStream os=new ObjectInputStream(fis);
		List<Movie> l=(List<Movie>) os.readObject();
		return l;
	}
	
	
	List<Movie> getMoviesReleasedInYear(int year) throws SQLException
	{
		Connection conn=DbConnection.getConnection();
		Statement stmt=conn.createStatement();
		ResultSet res=stmt.executeQuery("Select * from movie");
		List<Movie> movie=new ArrayList<>();
		while(res.next())
		{
			
			String[] temp=res.getDate(5).toString().split("-");
			if(Integer.parseInt(temp[0])==year)
			{
				String[] temp2=res.getString(6).split(",");
				List<String> l=new ArrayList<>();
				for(String s:temp2)
					l.add(s);
				Movie m=new Movie(res.getInt(1), res.getString(2), res.getString(3), res.getString(4), res.getDate(5),l, res.getDouble(7), res.getDouble(8));
				movie.add(m);
			}
			
		}
		conn.close();
		return movie;
	
	}
	
	List<Movie> getMoviesByActor(String... actorNames) throws SQLException
	{
		Connection conn=DbConnection.getConnection();
		Statement stmt=conn.createStatement();
		ResultSet res=stmt.executeQuery("Select * from movie");
		ArrayList<Movie> movies=new ArrayList<>();
		while(res.next())
		{
				String[] temp2=res.getString(6).split(",");
				List<String> l=new ArrayList<>();
				for(String s:temp2)
					l.add(s);
				for(String i:actorNames)
				{
					int flag=0;
					for(String j:l)
					{
						
						if(j.equals(i))
						{
							Movie m=new Movie(res.getInt(1), res.getString(2), res.getString(3), res.getString(4), res.getDate(5),l, res.getDouble(7), res.getDouble(8));
							movies.add(m);
							flag=1;
							break;
							
						}
					}
					if(flag==1)
						break;
		
				}
				
		}
		conn.close();
			return movies;
	}
	
	void updateRatings(Movie movie, double rating ,List<Movie> movies)
	{
 		
                  for(int i=0;i<movies.size();i++)
                  {
                	  if(movies.get(i).getMovieId()==movie.movieId) {
                		  movies.get(i).setRating(rating);
                		  break;
                	  }

                  }
	}
	
	void updateBusiness(Movie movie, double amount,List<Movie> movies) {
		for(int i=0;i<movies.size();i++)
        {
      	  if(movies.get(i).getMovieId()==movie.movieId) {
      		  movies.get(i).setTotalBusinessDone(amount);
      		  break;
      	  }

        }

	}
	
	Set<Movie> businessDone(double amount) throws SQLException
	{
		Connection conn=DbConnection.getConnection();
		Statement stmt=conn.createStatement();
		ResultSet res=stmt.executeQuery("Select * from movie");
		List<Movie> movie=new ArrayList<>();
		while(res.next())
		{
			if(res.getDouble(8)>amount) {
				String[] temp2=res.getString(6).split(",");
				List<String> l=new ArrayList<>();
				for(String s:temp2)
					l.add(s);
				Movie m=new Movie(res.getInt(1), res.getString(2), res.getString(3), res.getString(4), res.getDate(5),l, res.getDouble(7), res.getDouble(8));
				movie.add(m);
			     }
		}
		Collections.sort(movie,new MovComparator());
		HashSet<Movie> hashSet=new HashSet<>();
		hashSet.addAll(movie);
		conn.close();
		return hashSet;	
	}
	
}
	

