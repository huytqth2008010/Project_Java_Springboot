package com.example.project_java_springboot.seeder;

import com.example.project_java_springboot.entity.*;
import com.example.project_java_springboot.entity.enums.*;
import com.example.project_java_springboot.repository.*;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
public class DataSeeder implements CommandLineRunner {
    private final Faker faker = new Faker();
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final AccountRepository accountRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final RolesRepository rolesRepository;
    private final PermissionRepository permissionRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;
    private final CommentRepository commentRepository;
    private final RatingRepository ratingRepository;
    private final ReplyRepository replyRepository;
    private final SlideRepository slideRepository;


    public DataSeeder(CategoryRepository categoryRepository, OrderRepository orderRepository, ProductRepository productRepository, AccountRepository accountRepository, ShoppingCartRepository shoppingCartRepository, RolesRepository rolesRepository, PermissionRepository permissionRepository, PasswordEncoder passwordEncoder, CustomerRepository customerRepository, CommentRepository commentRepository, RatingRepository ratingRepository, ReplyRepository replyRepository, SlideRepository slideRepository) {
        this.categoryRepository = categoryRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.accountRepository = accountRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.rolesRepository = rolesRepository;
        this.permissionRepository = permissionRepository;
        this.passwordEncoder = passwordEncoder;
        this.customerRepository = customerRepository;
        this.commentRepository = commentRepository;
        this.ratingRepository = ratingRepository;
        this.replyRepository = replyRepository;
        this.slideRepository = slideRepository;
    }

    public static List<String> fakeImages = new ArrayList<>();
    public static List<String> fakePayment = new ArrayList<>();
    public static List<String> fakerEmail = new ArrayList<>();

    @Override
    public void run(String... args) throws Exception {
        fakeImages.add("https://afamilycdn.com/150157425591193600/2022/2/25/com-rang-thap-cam-2-16457750259421468698180.jpg");
        fakeImages.add("https://bloganchoi.com/wp-content/uploads/2020/07/cach-lam-com-chien-trung-omurice-ngon-kho-cuong-0.jpg");
        fakeImages.add("https://www.thatlangon.com/wp-content/uploads/2020/11/mi-xao-bo-2-e1607316904890.jpg");
        fakeImages.add("https://tphcm.cdnchinhphu.vn/334895287454388224/2022/1/12/bun-bo-u-thung-16419442121621454559594.jpg");
        fakeImages.add("https://cdn.tgdd.vn/Files/2020/04/03/1246339/cach-nau-bun-ca-ha-noi-thom-ngon-chuan-vi-khong-ta-14-760x367.jpg");
        fakeImages.add("https://nghebep.com/wp-content/uploads/2018/08/cach-lam-bun-moc-gio-heo.jpg");
        fakeImages.add("https://statics.vinpearl.com/com-tam-ngon-o-sai-gon-0_1630562640.jpg");
        fakeImages.add("https://wecheckin.vn/wp-content/uploads/2019/11/quan-com-ngon-binh-dan-tai-ha-noi-wecheckin-9.jpg");
        fakeImages.add("https://statics.vinpearl.com/com-am-phu-hue-02_1631074972.jpg");

        fakePayment.add("Thanh toán bằng thẻ");
        fakePayment.add("Thanh toán bằng séc trực tuyến");
        fakePayment.add("Thanh toán bằng ví điện tử");

        fakerEmail.add("fakegmail1@gmail1.com");
        fakerEmail.add("fakegmail1@gmail2.com");
        fakerEmail.add("fakegmail1@gmail3.com");
        fakerEmail.add("fakegmail1@gmail4.com");
        fakerEmail.add("fakegmail1@gmail5.com");

        seedCategory();
        seedProduct();
        seedRoles();
        seedAccount();
        seedShoppingCart();
        seedOrder();
        seedCustomer();
        seedComment();
        seedReply();
        seedSlide();
        seedRating();
    }

    private void seedRating() {
        if (ratingRepository.findAll().isEmpty()) {
            List<Rating> ratings = new ArrayList<>();
            List<Account> accounts = accountRepository.findAll();
            List<Product> products = productRepository.findAll();
            for (int i = 0; i < 10; i++) {
                Account randomAccount = accounts.get(faker.number().numberBetween(0, accounts.size() - 1));
                Product randomProduct = products.get(faker.number().numberBetween(0, accounts.size() - 1));
                Rating rating = new Rating();
                rating.setId_account(randomAccount.getId());
                rating.setId_product(randomProduct.getId());
                rating.setRating_star((double) faker.number().numberBetween(1,5));
                rating.setStatus(EnumStatus.ACTIVE);
                ratings.add(rating);
            }
            ratingRepository.saveAll(ratings);
        }
    }

    private void seedSlide() {
        if (slideRepository.findAll().isEmpty()) {
            List<Slide> slides = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                Slide slide = new Slide();
                slide.setThumbnail(fakeImages.get(faker.number().numberBetween(0, fakeImages.size() - 1)));
                slide.setNote(faker.name().title());
                slide.setStatus(EnumStatus.ACTIVE);
                slides.add(slide);
            }
            slideRepository.saveAll(slides);
        }
    }

    private void seedRoles() {
        if (rolesRepository.findAll().isEmpty()) {
            List<Roles> roles = new ArrayList<>();

            Permission permission1 = new Permission();
            permission1.setName("allow:admins");
            permission1.setUrl("/api/v1/admin/**");

            Permission permission2 = new Permission();
            permission2.setName("read:orders");
            permission2.setMethod(MethodsConstant.GET);
            permission2.setUrl("/api/v1/orders");

            Permission permission3 = new Permission();
            permission3.setName("create:orders");
            permission3.setMethod(MethodsConstant.POST);
            permission3.setUrl("/api/v1/orders");

            Permission permission4 = new Permission();
            permission4.setName("read:shopping-cart");
            permission4.setMethod(MethodsConstant.GET);
            permission4.setUrl("/api/v1/shopping-cart");

            Permission permission5 = new Permission();
            permission5.setName("create:shopping-cart");
            permission5.setMethod(MethodsConstant.POST);
            permission5.setUrl("/api/v1/shopping-cart");

            Set<Permission> permissionsAdmin = new HashSet<>();
            permissionsAdmin.add(permission1);

            Set<Permission> permissionsModerator = new HashSet<>();
            permissionsModerator.add(permission1);

            Set<Permission> permissionsUser = new HashSet<>();
            permissionsUser.add(permission2);
            permissionsUser.add(permission3);
            permissionsUser.add(permission4);
            permissionsUser.add(permission5);

            Roles role1 = new Roles();
            role1.setName("admin");
            role1.setPermissions(permissionsAdmin);

            Roles role2 = new Roles();
            role2.setName("user");
            role2.setPermissions(permissionsUser);

            Roles role3 = new Roles();
            role3.setName("moderator");
            role3.setPermissions(permissionsModerator);

            roles.add(role1);
            roles.add(role2);
            roles.add(role3);

            rolesRepository.saveAll(roles);
        }
    }

    private void seedAccount() {
        if (accountRepository.findAll().isEmpty()) {
            List<Account> accounts = new ArrayList<>();

            Account accountUser = new Account();
            accountUser.setUserName("user");
            accountUser.setPasswordHash(passwordEncoder.encode("abc123@"));
            accountUser.setRoles(Collections.singletonList(rolesRepository.findByName("user").get()));
            accountUser.setStatus(AccountStatus.ACTIVE);
            accounts.add(accountUser);

            Account accountAdmin = new Account();
            accountAdmin.setUserName("admin");
            accountAdmin.setPasswordHash(passwordEncoder.encode("abc123@"));
            accountAdmin.setRoles(rolesRepository.findAll());
            accountAdmin.setStatus(AccountStatus.ACTIVE);
            accounts.add(accountAdmin);

            Account accountModerator = new Account();
            accountModerator.setUserName("mod");
            accountModerator.setPasswordHash(passwordEncoder.encode("abc123@"));
            accountModerator.setRoles(Collections.singletonList(rolesRepository.findByName("moderator").get()));
            accountModerator.setStatus(AccountStatus.ACTIVE);
            accounts.add(accountModerator);

            accountRepository.saveAll(accounts);
        }
    }

    private void seedCategory() {
        if (categoryRepository.findAll().isEmpty()) {
            List<Category> categories = new ArrayList<>();

            for (int i = 0; i < 10; i++) {
                Category category = new Category();
                category.setName(faker.name().title());
                category.setThumbnail(fakeImages.get(faker.number().numberBetween(0, fakeImages.size() - 1)));
                category.setStatus(ProductStatus.ACTIVE);
                categories.add(category);
            }

            categoryRepository.saveAll(categories);
        }
    }
    private void seedComment() {
        if (commentRepository.findAll().isEmpty()) {
            List<Comment> comments = new ArrayList<>();
            List<Account> accounts = accountRepository.findAll();
            List<Product> products = productRepository.findAll();

            for (int i = 0; i < 10; i++) {
                Account randomAccount = accounts.get(faker.number().numberBetween(0, accounts.size() - 1));
                Product randomProduct = products.get(faker.number().numberBetween(0, accounts.size() - 1));

                Comment comment = new Comment();

                comment.setId_account(randomAccount.getId());
                comment.setId_product(randomProduct.getId());
                comment.setContent(faker.name().title());
                comment.setStatus(EnumStatus.ACTIVE);
                comments.add(comment);
            }
            commentRepository.saveAll(comments);
        }
    }
    private void seedReply() {
        if (replyRepository.findAll().isEmpty()) {
            List<Reply> replies = new ArrayList<>();
            List<Comment> comments = commentRepository.findAll();
            List<Account> accounts = accountRepository.findAll();
            List<Product> products = productRepository.findAll();

            for (int i = 0; i < 10; i++) {
                Account randomAccount = accounts.get(faker.number().numberBetween(0, accounts.size() - 1));
                Product randomProduct = products.get(faker.number().numberBetween(0, products.size() - 1));
                Comment randomComment = comments.get(faker.number().numberBetween(0, comments.size() - 1));

                Reply reply = new Reply();

                reply.setId_account(randomAccount.getId());
                reply.setId_product(randomProduct.getId());
                reply.setId_comment(randomComment.getId());
                reply.setContent(faker.name().title());
                reply.setStatus(EnumStatus.ACTIVE);
                replies.add(reply);
            }
            replyRepository.saveAll(replies);
        }
    }
    private void seedCustomer() {
        if (customerRepository.findAll().isEmpty()) {
            List<Account> accounts = accountRepository.findAll();
            List<Customer> customers = new ArrayList<>();

            for (int i = 0; i < 10; i++) {
                Account randomAccount = accounts.get(faker.number().numberBetween(0, accounts.size() - 1));
                Customer customer = new Customer();
                customer.setId_account(randomAccount.getId());
                customer.setName(faker.name().title());
                customer.setGender(faker.name().title());
                customer.setEmail(fakerEmail.get(faker.number().numberBetween(0, fakerEmail.size() - 1)));
                customer.setAddress(faker.address().city());
                customer.setPhone_number(faker.phoneNumber().cellPhone());
                customer.setStatus(EnumStatus.ACTIVE);
                customers.add(customer);
            }

            customerRepository.saveAll(customers);
        }
    }
    private void seedProduct() {
        if (productRepository.findAll().isEmpty()) {
            List<Product> products = new ArrayList<>();
            List<Category> categories = categoryRepository.findAll();

            for (int i = 0; i < 100; i++) {
                Product product = new Product();
                product.setName(faker.name().title());
                product.setThumbnail(fakeImages.get(faker.number().numberBetween(0, fakeImages.size() - 1)));
                product.setDescription(faker.lorem().sentence());
                product.setCost_price(new BigDecimal(faker.number().numberBetween(20000, 999999)));
                product.setUnit_price(new BigDecimal(faker.number().numberBetween(20000, 999999)));
                product.setQty(faker.number().numberBetween(1,10));
                product.setPromotion_price(new BigDecimal(faker.number().numberBetween(20000, 999999)));
                product.setCategoryId(categories.get(faker.number().numberBetween(0, categories.size() - 1)).getId());
                product.setStatus(ProductStatus.ACTIVE);
                products.add(product);
            }

            productRepository.saveAll(products);
        }
    }

    private void seedShoppingCart() {
        if (shoppingCartRepository.findAll().isEmpty()) {
            List<Account> accounts = accountRepository.findAll();
            List<Product> products = productRepository.findAll();

            List<ShoppingCart> shoppingCarts = new ArrayList<>();
            HashSet<String> existingAccountId = new HashSet<>();
            for (int i = 0; i < 5; i++) {
                Account randomAccount = accounts.get(faker.number().numberBetween(0, accounts.size() - 1));

                if (existingAccountId.contains(randomAccount.getId())) {
                    continue;
                }

                HashSet<String> existingProductId = new HashSet<>();

                ShoppingCart shoppingCart = new ShoppingCart();
                shoppingCart.setAccountId(randomAccount.getId());

                Set<CartItem> cartItems = new HashSet<>();
                int randomCartItem = faker.number().numberBetween(1, 5);
                for (int j = 0; j < randomCartItem; j++) {
                    Product randomProduct = products.get(faker.number().numberBetween(0, accounts.size() - 1));

                    if (existingProductId.contains(randomProduct.getId())) {
                        continue;
                    }

                    CartItem cartItem = CartItem.builder()
                            .id(new CartItemId(shoppingCart.getId(), randomProduct.getId()))
                            .productName(randomProduct.getName())
                            .productThumbnail(randomProduct.getThumbnail())
                            .quantity(faker.number().numberBetween(1, 3))
                            .unitPrice(randomProduct.getUnit_price())
                            .shoppingCart(shoppingCart)
                            .status(CartItemStatus.ACTIVE.getValue())
                            .cartItemStatus(CartItemStatus.ACTIVE)
                            .build();
                    cartItems.add(cartItem);
                    shoppingCart.addTotalPrice(cartItem);
                    existingProductId.add(randomProduct.getId());
                }
                shoppingCart.setCartItems(cartItems);
                shoppingCarts.add(shoppingCart);
                existingAccountId.add(randomAccount.getId());
            }

            shoppingCartRepository.saveAll(shoppingCarts);
        }
    }

    private void seedOrder() {
        if (orderRepository.findAll().isEmpty()) {
            List<Account> accounts = accountRepository.findAll();
            List<Product> products = productRepository.findAll();

            List<Order> orders = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                Account randomAccount = accounts.get(faker.number().numberBetween(0, accounts.size() - 1));

                Order order = new Order();
                order.setId("");
                order.setAccountId(randomAccount.getId());
                order.setPaid(new BigDecimal(faker.number().numberBetween(1,999)));
                order.setUnpaid(new BigDecimal(faker.number().numberBetween(1,999)));
                order.setShipName(faker.name().name());
                order.setShipAddress(faker.address().city());
                order.setShipPhone(faker.phoneNumber().cellPhone());
                order.setPayment_method(fakePayment.get(faker.number().numberBetween(0, fakePayment.size() - 1)));
                order.setShipNote(faker.lorem().sentence());

                Set<OrderDetail> orderDetails = new HashSet<>();
                HashSet<String> existingProductId = new HashSet<>();

                int randomOrderDetails = faker.number().numberBetween(1, 5);
                for (int j = 0; j < randomOrderDetails; j++) {
                    Product randomProduct = products.get(faker.number().numberBetween(0, accounts.size() - 1));

                    if (existingProductId.contains(randomProduct.getId())) {
                        continue;
                    }

                    OrderDetail orderDetail = OrderDetail.builder()
                            .id(new OrderDetailId(randomProduct.getId(), order.getId()))
                            .order(order)
                            .product(randomProduct)
                            .quantity(faker.number().numberBetween(1, 3))
                            .unitPrice(randomProduct.getUnit_price())
                            .build();
                    orderDetails.add(orderDetail);
                    order.addTotalPrice(orderDetail);
                    existingProductId.add(randomProduct.getId());
                }
                order.setStatus(OrderStatus.WAITING);
                order.setOrderDetails(orderDetails);
                orders.add(order);
            }

            orderRepository.saveAll(orders);
        }
    }
}
