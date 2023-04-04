import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList();

        int partitionSize;
        for(partitionSize = 1; partitionSize <= 10000; ++partitionSize) {
            numbers.add(partitionSize);
        }

        partitionSize = numbers.size() / 4;
        List<List<Integer>> partitions = new ArrayList();

        for(int i = 0; i < numbers.size(); i += partitionSize) {
            partitions.add(numbers.subList(i, Math.min(i + partitionSize, numbers.size())));
        }

        List<Thread> threads = new ArrayList();
        List<Integer> evenNumbers = new ArrayList();
        List<Integer> oddNumbers = new ArrayList();
        Iterator var7 = partitions.iterator();

        while(var7.hasNext()) {
            List<Integer> partition = (List)var7.next();
            Thread thread = new Thread(() -> {
                Iterator var3 = partition.iterator();

                while(var3.hasNext()) {
                    Integer number = (Integer)var3.next();
                    if (number % 2 == 0) {
                        synchronized(evenNumbers) {
                            evenNumbers.add(number);
                        }
                    } else {
                        synchronized(oddNumbers) {
                            oddNumbers.add(number);
                        }
                    }
                }

            });
            thread.start();
            threads.add(thread);
        }

        var7 = threads.iterator();

        while(var7.hasNext()) {
            Thread thread = (Thread)var7.next();

            try {
                thread.join();
            } catch (InterruptedException var10) {
                var10.printStackTrace();
            }
        }

        System.out.println("Even numbers: " + String.valueOf(evenNumbers));
        System.out.println("Odd numbers: " + String.valueOf(oddNumbers));
    }
}
