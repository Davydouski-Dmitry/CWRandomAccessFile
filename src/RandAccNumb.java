import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;//класс реализующий файл с произвольным доступом к данным
import java.util.Scanner;


//Работа с числовыми данными в файле с произвольным доступом

//Выполняем следующие задания:
// - записать в файл заданное количество целых чисел
// - прочитать данные в прямом и обратном порядке
// - получить информацию о файле и указателе до ввода и после ввода данных
// - отсортировать по возрастанию числа непосредственно в файле метод "Пузырька"

public class RandAccNumb {
    public static void main(String[] args) {
        try {
            //создаём папку
            File folder = new File("D:\\DimasJava");
            if(!folder.exists())//проверяем не существует ли такая папка
                folder.mkdir();

            //создаём файл в папке
            File fl = new File("D:\\DimasJava\\number1.txt");
            fl.createNewFile();

            Scanner sc = new Scanner(System.in,"cp1251");
            System.out.println("сколько чисел надо записать в файл? ");
            int count = sc.nextInt();//вводим количество чисел


            //открываем файл для чтения и записи "rw"
            RandomAccessFile rf = new RandomAccessFile(fl,"rw");
            System.out.println("Исходный размер файла в байтах =" + rf.length()+", указатель стоит на "+rf.getFilePointer()+"-м байте.");
            System.out.println("Введите числа:");
            for (int i=0;i<count;i++){
                rf.writeInt(sc.nextInt());//запись числа в файл!!
            }
            System.out.println("Новый размер файла в байтах =" + rf.length()+", указатель стоит на "+rf.getFilePointer()+"-м байте.");
            System.out.println("Количество байт на 1 число = "+rf.length()/count);
            rf.close();//закрываем файл





            //открываем файл ток для чтения "r"
            rf = new RandomAccessFile(fl,"r");
            //2. прочитать числа из файла и вывести на экран
            System.out.println("Числа в файле: ");
            for(int i=0;i<count;i++){
                //перевод указателя на текущее число, 4-тк тип int сток байт
                rf.seek(i*4);
                System.out.println("Число "+i+" : "+rf.readInt());
            }

            //3. прочитать числа в обратном порядке из файла и вывести на экран
            System.out.println("Числа в обратном порядке: ");
            for(int i=count-1;i>=0;i--){
                //перевод указателя на текущее число, 4-тк тип int сток байт
                rf.seek(i*4);
                System.out.println("Число"+i+" : "+rf.readInt());
            }

            //перевод указателя на последнее число
            rf.seek(rf.getFilePointer()-4);
            System.out.println("Количество чисел в файле ="+rf.length()/4 + ",последнее число=" +rf.readInt());


            //поиск заданого числа в файле и определение его номера
            System.out.println("Введите число, которое нужно найти в файле =");
            int x = sc.nextInt();
            int kol = 0;
            for(int i=0;i<count;i++){
                rf.seek(i*4);
                int number = rf.readInt();
                if(number == x){
                    kol++;
                    System.out.println("номер: число "+i+", ");
                }
            }
            System.out.println("количество искомых чисел " + kol);
            rf.close();//закрываем файл



            //4. Cортировка чисел в файле методом "Пузырька"
            rf = new RandomAccessFile(fl,"rw");

            for (int k=0; k<count; k++){
                for (int i=0;i<count-k-1;i++){
                    rf.seek(i*4);
                    int number1 = rf.readInt();
                    int number2 = rf.readInt();
                    if(number1>number2){
                        rf.seek(i*4);
                        rf.writeInt(number2);
                        rf.writeInt(number1);
                    }
                }
            }
            System.out.println("Числа отсортированные в файле: ");
            for(int i=0;i<count;i++){
                rf.seek(i*4);
                System.out.println(" "+rf.readInt());
            }
            rf.close();


        } catch (IOException e) {
            System.out.println("Завершение программы, ошибка - "+e);
        }
    }
}
