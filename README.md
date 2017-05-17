# cplugins
As plugin module for all projects


# plugin-rpc
Implemented a method which simply add some annotation on implemented service, and we can export and import service through RMI with host and port configured in application.properties file.

Add `@CRmiExport` annotation on implemented service which you want to export, add `@CRmiImport` annotation on the implemented service and add `@CRmiImportService` annotation on the filed which you want import through RMI.

By the way, do not forget add `@Lazy` on the filed, and this annotation may just works well with Spring 4.

`
@Service("commentService")
@CRmiImport
@CRmiExport
public class CommentServiceImpl implements CommentService{
    // ... 
    @Autowired
    //@Resource(name = "userService")
    @Lazy
    @CRmiImportService
    private UserService userService;
`

Of course, you should configure RMI export port, import host and port in application.properties and it will be injected into CRmiProperties bean by Spring Boot's configuration processor
