package com.example.ecommercebackend.util;

import com.example.ecommercebackend.config.AppConstants;
import com.example.ecommercebackend.model.*;
import com.example.ecommercebackend.repository.*;
import com.example.ecommercebackend.service.CartService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner initData(
            RoleRepository roleRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            CartService cartService,
            ProductRepository productRepository,
            CategoryRepository categoryRepository,
            AddressRepository addressRepository
    ) {
        return args -> {

            // Retrieve or create roles
            Role userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
                    .orElseGet(() -> {
                        Role newUserRole = new Role(AppRole.ROLE_USER);
                        return roleRepository.save(newUserRole);
                    });

            Role sellerRole = roleRepository.findByRoleName(AppRole.ROLE_SELLER)
                    .orElseGet(() -> {
                        Role newSellerRole = new Role(AppRole.ROLE_SELLER);
                        return roleRepository.save(newSellerRole);
                    });

            Role adminRole = roleRepository.findByRoleName(AppRole.ROLE_ADMIN)
                    .orElseGet(() -> {
                        Role newAdminRole = new Role(AppRole.ROLE_ADMIN);
                        return roleRepository.save(newAdminRole);
                    });

            Set<Role> userRoles = Set.of(userRole);
            Set<Role> sellerRoles = Set.of(sellerRole);
            Set<Role> adminRoles = Set.of(userRole, sellerRole, adminRole);

            //
            Address userAddress = new Address();
            Address adminAddress = new Address();

            // Create users if not already present
            if (!userRepository.existsByUserName("user1")) {
                User user1 = new User("user1", "user1@example.com", passwordEncoder.encode("password1"));
                userRepository.save(user1);
            }

            if (!userRepository.existsByUserName("seller1")) {
                User seller1 = new User("seller1", "seller1@example.com", passwordEncoder.encode("password2"));
                userRepository.save(seller1);
            }

            if (!userRepository.existsByUserName("admin")) {
                User admin = new User("admin", "admin@example.com", passwordEncoder.encode("adminPass"));
                adminAddress.setUser(admin);
                userRepository.save(admin);
            }

            // Update roles for existing users
            userRepository.findByUserName("user1").ifPresent(user -> {
                user.setRoles(userRoles);
                userAddress.setUser(user);
                userRepository.save(user);
            });

            userRepository.findByUserName("seller1").ifPresent(seller -> {
                seller.setRoles(sellerRoles);
                userRepository.save(seller);
            });

            userRepository.findByUserName("admin").ifPresent(admin -> {
                admin.setRoles(adminRoles);
                userRepository.save(admin);
            });


            userAddress.setStreet("Datta Mandir");
            userAddress.setCity("Pune");
            userAddress.setCountry("India");
            userAddress.setPincode("412207");
            userAddress.setState("Maharashtra");
            userAddress.setBuildingName("Silver Crest");

            adminAddress.setStreet("Malad Street");
            adminAddress.setCity("Mumbai");
            adminAddress.setCountry("India");
            adminAddress.setPincode("412207");
            adminAddress.setState("Maharashtra");
            adminAddress.setBuildingName("Golden Crest");

            addressRepository.save(userAddress);
            addressRepository.save(adminAddress);

            //
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            Authentication auth = new UsernamePasswordAuthenticationToken("user1", "password1", List.of(new SimpleGrantedAuthority(AppConstants.ADMIN)));
            context.setAuthentication(auth);
            SecurityContextHolder.setContext(context);

            // Create categories
            Category electronics = new Category();
            electronics.setCategoryName("Electronics");

            Category fashion = new Category();
            fashion.setCategoryName("Fashion");

            Category homeAppliance = new Category();
            homeAppliance.setCategoryName("Home Appliances");

            Category books = new Category();
            books.setCategoryName("Books");

            // Persist categories
            electronics = categoryRepository.save(electronics);
            fashion = categoryRepository.save(fashion);
            homeAppliance = categoryRepository.save(homeAppliance);
            books = categoryRepository.save(books);

            // Create products
            Product mobile = new Product();
            mobile.setProductName("Smartphone");
            mobile.setProductImage("https://images-cdn.ubuy.co.in/65fe92b11e31136d9375c88c-mini-smartphone-child-phone-the.jpg");
            mobile.setCategory(electronics);
            mobile.setPrice(15000.0);
            mobile.setQuantity(10);

            Product tshirt = new Product();
            tshirt.setProductName("T-Shirt");
            tshirt.setProductImage("https://thebanyantee.com/cdn/shop/files/Black-T-shirt.jpg?v=1721380366");
            tshirt.setCategory(fashion);
            tshirt.setPrice(500.0);
            tshirt.setQuantity(50);

            Product washingMachine = new Product();
            washingMachine.setProductName("Washing Machine");
            washingMachine.setProductImage("https://whirlpoolindia.vtexassets.com/assets/vtex.file-manager-graphql/images/cd054d04-0341-407e-ae36-be960d4d86b3___6c49c859c2d58529094d2822a3ceb555.jpg");
            washingMachine.setCategory(homeAppliance);
            washingMachine.setPrice(20000.0);
            washingMachine.setQuantity(5);

            Product refrigerator = new Product();
            refrigerator.setProductName("Refrigerator");
            refrigerator.setProductImage("https://www.livemint.com/lm-img/img/2024/11/22/600x338/refrigerator_under_35000_1732276968144_1732276974547.jpg");
            refrigerator.setCategory(homeAppliance);
            refrigerator.setPrice(25000.0);
            refrigerator.setQuantity(8);

            Product novel = new Product();
            novel.setProductName("Novel");
            novel.setProductImage("https://tariqumrani.blog/wp-content/uploads/2024/04/english-novel.webp?w=1024");
            novel.setCategory(books);
            novel.setPrice(300.0);
            novel.setQuantity(100);

            Product tablet = new Product();
            tablet.setProductName("Tablet");
            tablet.setProductImage("https://aws-obg-image-lb-4.tcl.com/content/dam/brandsite/region/global/products/tablets/tcl-nxtpaper-14/id/1.png?t=1721272443153&w=800&webp=undefined&dpr=2.625&rendition=1068");
            tablet.setCategory(electronics);
            tablet.setPrice(10000.0);
            tablet.setQuantity(12);

            Product hoodie = new Product();
            hoodie.setProductName("Hoodie");
            hoodie.setProductImage("https://m.media-amazon.com/images/I/513QvuEc9OL._AC_UY1100_.jpg");
            hoodie.setCategory(fashion);
            hoodie.setPrice(800.0);
            hoodie.setQuantity(40);

            Product headphones = new Product();
            headphones.setProductName("Headphones");
            headphones.setProductImage("https://shopatsc.com/cdn/shop/products/CH520_1000x1000_Blue_G.jpg?v=1681219211");
            headphones.setCategory(electronics);
            headphones.setPrice(2500.0);
            headphones.setQuantity(25);

            Product shoes = new Product();
            shoes.setProductName("Shoes");
            shoes.setProductImage("https://fausto.in/cdn/shop/products/FST_FOBWC-1503_GOLDEN_1-1_MOOD_6611f676-23f5-4eb0-bd7b-c527c4123b70.jpg?v=1679576984");
            shoes.setCategory(fashion);
            shoes.setPrice(2000.0);
            shoes.setQuantity(30);

            Product blender = new Product();
            blender.setProductName("Blender");
            blender.setProductImage("https://m.media-amazon.com/images/I/81p-MlSSh2L._AC_UF894,1000_QL80_.jpg");
            blender.setCategory(homeAppliance);
            blender.setPrice(1500.0);
            blender.setQuantity(15);

            Product camera = new Product();
            camera.setProductName("Camera");
            camera.setProductImage("https://i.pinimg.com/736x/e7/5d/db/e75ddbda351d44e24b6b8099fa200aad.jpg");
            camera.setCategory(electronics);
            camera.setPrice(35000.0);
            camera.setQuantity(7);

            //
            double mobilePriceToUse = Optional.ofNullable(mobile.getSpecialPrice())
                    .orElse(mobile.getPrice()); // Default to regular price if specialPrice is null

            double tshirtPriceToUse = Optional.ofNullable(tshirt.getSpecialPrice())
                    .orElse(tshirt.getPrice()); // Default to regular price if specialPrice is null

            double washingMachinePriceToUse = Optional.ofNullable(washingMachine.getSpecialPrice())
                    .orElse(washingMachine.getPrice());

            double refrigeratorPriceToUse = Optional.ofNullable(refrigerator.getSpecialPrice())
                    .orElse(refrigerator.getPrice());

            double novelPriceToUse = Optional.ofNullable(novel.getSpecialPrice())
                    .orElse(novel.getPrice());

            double tabletPriceToUse = Optional.ofNullable(tablet.getSpecialPrice())
                    .orElse(tablet.getPrice());

            double hoodiePriceToUse = Optional.ofNullable(hoodie.getSpecialPrice())
                    .orElse(hoodie.getPrice());

            double headphonesPriceToUse = Optional.ofNullable(headphones.getSpecialPrice())
                    .orElse(headphones.getPrice());

            double shoesPriceToUse = Optional.ofNullable(shoes.getSpecialPrice())
                    .orElse(shoes.getPrice());

            double blenderPriceToUse = Optional.ofNullable(blender.getSpecialPrice())
                    .orElse(blender.getPrice());

            double cameraPriceToUse = Optional.ofNullable(camera.getSpecialPrice())
                    .orElse(camera.getPrice());

            // Set special prices for all products
            mobile.setSpecialPrice(mobilePriceToUse);
            tshirt.setSpecialPrice(tshirtPriceToUse);
            mobile.setSpecialPrice(mobilePriceToUse);
            tshirt.setSpecialPrice(tshirtPriceToUse);
            washingMachine.setSpecialPrice(washingMachinePriceToUse);
            refrigerator.setSpecialPrice(refrigeratorPriceToUse);
            novel.setSpecialPrice(novelPriceToUse);
            tablet.setSpecialPrice(tabletPriceToUse);
            hoodie.setSpecialPrice(hoodiePriceToUse);
            headphones.setSpecialPrice(headphonesPriceToUse);
            shoes.setSpecialPrice(shoesPriceToUse);
            blender.setSpecialPrice(blenderPriceToUse);
            camera.setSpecialPrice(cameraPriceToUse);


            // Persist products
            productRepository.saveAll(List.of(mobile, tshirt, washingMachine, refrigerator, novel,
                    tablet, hoodie, headphones, shoes, blender, camera));

            // Add products to cart with quantities
            cartService.addProductToCart(mobile.getProductId(), 2);
//            cartService.addProductToCart(tshirt.getProductId(), 3);
//            cartService.addProductToCart(washingMachine.getProductId(), 1);
//            cartService.addProductToCart(refrigerator.getProductId(), 1);
//            cartService.addProductToCart(novel.getProductId(), 5);
//            cartService.addProductToCart(tablet.getProductId(), 2);
//            cartService.addProductToCart(hoodie.getProductId(), 4);
//            cartService.addProductToCart(headphones.getProductId(), 2);
//            cartService.addProductToCart(shoes.getProductId(), 3);
//            cartService.addProductToCart(blender.getProductId(), 2);
//            cartService.addProductToCart(camera.getProductId(), 1);
        };
    }
}
