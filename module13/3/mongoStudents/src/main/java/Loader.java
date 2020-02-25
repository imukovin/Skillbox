public class Loader {
    public static void main(String[] args) {
        Parse parseToMongo = new Parse();

        System.out.println("Общее число студенотов: " + parseToMongo.getStudentsCount());
        System.out.println("Количество студенотов возраст > 40: " + parseToMongo.getStudentsAgeMoreFourteen());
        System.out.println("Имена самых молодых студентов: " + parseToMongo.getNamesYoungOrCoursesOfOldStudents(1));
        System.out.println("Курсы самого старого студента: " + parseToMongo.getNamesYoungOrCoursesOfOldStudents(-1));
    }
}
