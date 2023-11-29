#  Project that leverages a concurrent approach to text processing.


Given text:

"Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium 
doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis 
et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem 
quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores 
eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem 
ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius 
modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut 
enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit 
laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure 
reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum 
qui dolorem eum fugiat quo voluptas nulla pariatur"


* It is necessary to make transformations and statistics of the original text:
  * change the arrangement of letters in each word so that the position of the first and last is kept the same
    and the position of the other letters will be permuted in a random arrangement.
  * reverse the arrangement of letters in each word while keeping a capital letter at the beginning of the sentence.
  * reverse the order of words in the sentence while retaining the capital letter at the beginning of the sentence.
  * reverse order of sentences in the text.
  * make statistics of the occurrence of vowels by vowel and by sentence.
 
* The requested transformations should be implemented so that they are executed concurrently.
For implementation, use classes from the Java 8 SE framework (or later).




Run options:

Run application with 5 threads for 5 task and 0ms of delay between tasks
```
docker run <image_name>
```
Run application with specified number of threads for 5 task and 0 miliseconds of delay between tasks action
```
docker run <image_name> <num_of_threads>
```

Run application with specified number of threads for 5 task and spedcified delay in miliseconds of delay between tasks action
```
docker run <image_name> <num_of_threads> <task_delay_1> ... <task_delay_5>
```
