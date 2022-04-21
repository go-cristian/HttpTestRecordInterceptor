# Test Record Interceptor

est Record Interceptor allows to record responses from http request to be replayed at tests.

## How it works

Use regular http calls in your tests, just add the interceptor, run the tests to record, then replay all the times.

## Installation

Use next dependencies into your build.gradle
```groovy
testImplementation 'dev.cgomez.testrecordinterceptor:test-record-interceptor:0.0.1'
```

## Example

On your tests you can use call your code that depends on okhttp client's implementation.

```kotlin
@Test
fun `should perform a request that is recorded`() {
    val client =
        OkHttpClient.Builder().addInterceptor(TestFileSaverInterceptor("cached")).build()
    val repository = DittoRepository(client)
    runBlocking {
        val response = repository.call()
        assertNotNull(response)
    }
}
```

# License

    Copyright 2021 Cristian Gomez

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
