# Reflection 1

In this module, I have applied several Clean Code principles such as Meaningful Names for my controllers and services. I also practiced Secure Coding by using UUIDs instead of predictable numeric IDs to prevent direct object reference attacks. However, I realized my Input Validation is still weak because I haven't checked for negative quantities, which I plan to fix using Spring Validation in the next iteration.

# Reflection 2

1. Writing unit tests gives me a sense of security, knowing that my code functions as intended. In my opinion, the number of tests should depend on the complexity of the methods.
To ensure sufficiency, I use Code Coverage tools. However, even with 100% coverage, it doesn't mean the code is bug-free. Coverage only shows that the code has been executed, but it doesn't guarantee that the test cases cover every possible edge case or logical error, such as unexpected user input or integration issues.
2. If I create a new functional test suite by copying the setup procedures and instance variables from the previous suite, the code quality will likely decrease due to Code Duplication (DRY principle violation).

Having identical setup code in multiple classes makes the project harder to maintain. For instance, if the server configuration changes, I would need to update every single test class manually.

To improve this, I should apply a Base Class approach. I can move the common setup (like @LocalServerPort and baseUrl initialization) into a parent class and have all functional test classes inherit from it. This way, the code remains clean, modular, and easier to maintain

# Reflection 3
1. Principles I apply:
- SRP: I separated CarController from the ProductController.java file. Previously, one file handled two different domains; now, each controller has a single reason to change, focusing only on its respective entity.
- DIP: I introduced the IRepository<T> generic interface. Now, high-level modules like ProductServiceImpl and CarServiceImpl depend on the abstraction (IRepository) rather than the concrete implementations (ProductRepository or CarRepository). I implemented Constructor Injection in the Controllers and Services. This removes the tight coupling caused by using @Autowired directly on fields.
- LSP: By removing the unnecessary inheritance where CarController used to extend ProductController (in the previous version), I ensured that CarController does not inherit behaviors it doesn't need. The use of IRepository<T> ensures that any implementation (Product or Car) can be used interchangeably wherever the interface is required without breaking the application's logic.
- ISP: The IRepository interface provides a lean set of methods required for CRUD operations. If a model only requires "Read" access in the future, we can easily split this into smaller interfaces (e.g., IReadRepository), ensuring classes aren't forced to implement methods they don't use.

2. Advantages of Applying SOLID Principles:
- Enhanced Maintainability:By applying SRP, we separated CarController from ProductController. If we want to change the URL mapping for car-related pages (e.g., from /car/listCar to /car/all), we only need to modify CarController. We don't have to worry about accidentally changing or breaking the ProductController logic because they are no longer in the same file. This makes the code much safer and easier to update.
- Easier Testing: By using Constructor Injection, I can easily perform Unit Testing using Mockito. I can inject a "Mock Repository" into the CarService during tests without needing to instantiate the actual data list.
- Reduced Code Duplication: Using a Generic Interface (IRepository<T>) allows us to standardize the behavior of all repositories. We don't have to redefine the structure of create, find, or delete for every new model we add.

3. Disadvantages of Not Applying SOLID Principles:
- Rigidity and Fragility: If CarController remained inside ProductController.java (violating SRP), any change to the Car logic might accidentally break the Product logic because they share the same file and potentially the same dependencies.
- Tight Coupling: Without DIP, if the Service is hard-coded to use ProductRepository (concrete class), replacing the storage system would require a complete rewrite of the Service layer, making the system very difficult to evolve.
- Spaghetti Code: Without LSP, forcing Car to inherit from Product just to "save code" would lead to "hacky" fixes where Car might have to throw exceptions for Product methods it doesn't actually support, making the code unpredictable and prone to bugs.

# Reflection 4

1. Based on the questions proposed by Percival (2017), I believe this TDD flow has been highly effective for my development process:
- Does it give me confidence? Absolutely. For instance, when I implemented the Order model, I already had a test case for testCreateOrderEmptyProduct. This gave me the confidence that my system would never allow an invalid order (one without products) to exist in the database.
- Does it help me with design? Yes. Writing the tests for OrderRepository first helped me design the Update or Insert logic. I had to decide how the repository should behave when saving an order with an existing ID versus a new one before I even wrote the save() method.
- Does it stay out of my way? The tests are concise and focused. Because I used Mockito in the Service tests and simple JUnit assertions in the Model/Repository tests, the testing suite remains maintainable and doesn't hinder my productivity.

2. After reviewing the tests for Order, OrderRepository, and OrderService, I can conclude that they successfully follow the F.I.R.S.T. principles:
- Fast: My tests are extremely fast. The OrderRepositoryTest uses a simple ArrayList in-memory, and OrderServiceTest uses mocks, so there is no slow database or network overhead.
- Independent: Each test is isolated. In OrderRepositoryTest, I use @BeforeEach to re-initialize the orderRepository and the sample orders list, ensuring that data from one test case doesn't leak into another.
- Repeatable: The tests are deterministic. Whether I run them on my local machine or a different environment, the results stay the same because they don't depend on external states.
- Self-Validating: I used clear assertions like assertEquals, assertNull, and assertThrows. The IDE provides a clear green checkmark or a red cross, leaving no room for manual interpretation of the results.
- Thorough/Timely: My tests are timely because they were created alongside the implementation. They are also thorough; for example, in OrderTest, I didn't just test the "Happy Path" (success), but also ensured that an IllegalArgumentException is thrown when an invalid status like "MEOW" is passed.