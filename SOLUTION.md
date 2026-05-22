# Solution Steps

1. Create a custom runtime exception, `MedicationNotFoundException`, that accepts the missing medication code and builds the message `Medication with code '<code>' not found`. Keep the code as a field so it can also be logged or reused by handlers.

2. Change the service-layer lookup logic in `MedicationService#getByCode` so it never returns `null`. Replace `orElse(null)` with `orElseThrow(() -> new MedicationNotFoundException(code))`. This fixes the root cause of the controller returning HTTP 200 with an empty body.

3. Keep the controller simple. `MedicationController` should continue calling the service and returning the `Medication` object directly for successful lookups. No special null checks are needed once the service throws an exception for missing data.

4. Add a small JSON error DTO such as `ApiErrorResponse` with a single `message` field. This gives the API a consistent error response body.

5. Implement a global exception handler using `@RestControllerAdvice`. Add an `@ExceptionHandler(MedicationNotFoundException.class)` method that returns `ResponseEntity.status(HttpStatus.NOT_FOUND)` with `new ApiErrorResponse(exception.getMessage())` as the body.

6. Add logging in both the service and exception handler so successful lookups, missing records, and generated 404 responses are observable in application logs.

7. Update `application.properties` to include `spring.jpa.defer-datasource-initialization=true` so Hibernate creates the `medications` table before `data.sql` runs. This keeps the application runnable with the provided seed data.

8. Run the tests. The happy-path test should still return HTTP 200 with medication JSON, and the missing-resource test should now return HTTP 404 with a JSON body containing the not-found message.

