package chupiak;

import chupiak.config.AppConfig;
import chupiak.exception.AuthenticationException;
import chupiak.model.CinemaHall;
import chupiak.model.Movie;
import chupiak.model.MovieSession;
import chupiak.model.ShoppingCart;
import chupiak.model.User;
import chupiak.security.AuthenticationService;
import chupiak.service.CinemaHallService;
import chupiak.service.MovieService;
import chupiak.service.MovieSessionService;
import chupiak.service.OrderService;
import chupiak.service.ShoppingCartService;
import chupiak.service.UserService;
import java.time.LocalDateTime;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) throws AuthenticationException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        MovieService movieService = context.getBean(MovieService.class);
        Movie movie = new Movie();
        movie.setTitle("Terminator");
        movie.setDescription("Action about robots");
        movieService.add(movie);
        System.out.println("Movie was found by id:" + movieService.get(1L).get().getId() + " is "
                + movieService.get(1L).get());
        System.out.println("All movies was found: " + movieService.getAll());

        CinemaHallService cinemaHallService = context.getBean(CinemaHallService.class);
        CinemaHall cinemaHallSmall = new CinemaHall();
        cinemaHallSmall.setCapacity(100);
        cinemaHallSmall.setDescription("Small hall");
        cinemaHallService.add(cinemaHallSmall);
        CinemaHall cinemaHallMedium = new CinemaHall();
        cinemaHallMedium.setCapacity(200);
        cinemaHallMedium.setDescription("Medium hall");
        cinemaHallService.add(cinemaHallMedium);
        System.out.println("Cinema hall was found by id:" + cinemaHallService.get(1L).getId()
                + " is " + cinemaHallService.get(1L));
        System.out.println("All cinema's halls was found: " + cinemaHallService.getAll());

        final MovieSessionService movieSessionService = context.getBean(MovieSessionService.class);
        MovieSession tomorrowMovieSession = new MovieSession();
        tomorrowMovieSession.setCinemaHall(cinemaHallSmall);
        tomorrowMovieSession.setMovie(movie);
        tomorrowMovieSession.setShowTime(LocalDateTime.now().plusDays(1L));
        movieSessionService.add(tomorrowMovieSession);
        MovieSession yesterdayMovieSession = new MovieSession();
        yesterdayMovieSession.setCinemaHall(cinemaHallMedium);
        yesterdayMovieSession.setMovie(movie);
        yesterdayMovieSession.setShowTime(LocalDateTime.now().minusDays(1L));
        movieSessionService.add(yesterdayMovieSession);
        System.out.println("Available movie session tomorrow: "
                + movieSessionService.findAvailableSessions(1L, tomorrowMovieSession
                .getShowTime().toLocalDate()));
        System.out.println("Movie session was yesterday: " + movieSessionService
                .findAvailableSessions(1L, yesterdayMovieSession.getShowTime().toLocalDate()));

        final AuthenticationService authenticationService
                = context.getBean(AuthenticationService.class);
        final UserService userService = context.getBean(UserService.class);
        User bob = new User();
        bob.setEmail("bob@gmail.com");
        bob.setPassword("1234");
        User registeredUserBob = authenticationService.register(bob.getEmail(), bob.getPassword());
        authenticationService.login(bob.getEmail(), bob.getPassword());
        System.out.println("User was found by email:" + userService.findByEmail("bob@gmail.com")
                .get().getEmail() + " is " + userService.findByEmail("bob@gmail.com").get());

        ShoppingCartService shoppingCartService = context.getBean(ShoppingCartService.class);
        shoppingCartService.addSession(tomorrowMovieSession, registeredUserBob);
        shoppingCartService.addSession(tomorrowMovieSession, registeredUserBob);
        ShoppingCart bobShoppingCart = shoppingCartService.getByUser(registeredUserBob);
        System.out.println("Shopping cart was found by user:" + shoppingCartService
                .getByUser(registeredUserBob).getUser().getEmail() + " is " + shoppingCartService
                .getByUser(registeredUserBob));

        OrderService orderService = context.getBean(OrderService.class);
        System.out.println("Order was completed !: " + orderService.completeOrder(bobShoppingCart));
        shoppingCartService.clearShoppingCart(bobShoppingCart);
    }
}
