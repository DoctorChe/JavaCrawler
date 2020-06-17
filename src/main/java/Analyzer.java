import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Analyzer {

    private static List<String> shingles(List<String> words, int shinglesLen) {
        List<String> result = new ArrayList<>();
        // перебираем список
        for (int i=0; i<(words.size() - shinglesLen + 1); i++)
        {
            String shingle = "";
            // объединяем элементы списка в тройки
            for (int j=0; j<shinglesLen; j++) shingle += words.get(i + j);
            result.add(shingle);
        }
        return result;
    }

    private static List<String> shingles(List<String> words) {
        List<String> result = new ArrayList<>();
        int shinglesLen = 3;
        // перебираем список
        for (int i=0; i<(words.size() - shinglesLen + 1); i++)
        {
            String shingle = "";
            // объединяем элементы списка в тройки
            for (int j=0; j<shinglesLen; j++) shingle += words.get(i + j);
            result.add(shingle);
        }
        return result;
    }

    private static double getMinHash(List<String> shinglesList1, List<String> shinglesList2) {
        HashSet<String> shinglesSet1 = new HashSet<>(shinglesList1);
        HashSet<String> shinglesSet2 = new HashSet<>(shinglesList2);

        MinHash minHash = new MinHash(shinglesSet1.size() + shinglesSet2.size());

        return minHash.similarity(shinglesSet1, shinglesSet2);
    }

    public static double shinglesMinHash(List<String> words1, List<String> words2) {
        return getMinHash(shingles(words1), shingles(words2));
    }

}
