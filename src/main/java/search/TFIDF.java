/*
 *  MIT License
 *
 *  Copyright (c) 2019 Michael Pogrebinsky - Distributed Systems & Cloud Computing with Java
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

package search;

import model.DocumentData;

import java.util.*;

public class TFIDF {
    private static ArrayList<String> noun = new ArrayList<>(Arrays.asList("شركة", "مشروع","بنك","مصرف","صندوق","الصندوق","مصفاة","معمل","مصنع","مجموعة", "القابضة"));
    private static ArrayList<String> legal = new ArrayList<>(Arrays.asList("محكمة","قضية","غرامة","غير شرعي","غير قانوني","متهمة","اتهامات","السجن","اتهمت","تحقيق","المحاكمة" ,"يتهم"));
    private static ArrayList<String> esg = new ArrayList<>(Arrays.asList("فساد","الفساد", "تعسفي" ,"رشوة","رشاوي", "ظروف عمل" ,"غسيل أموال","التآمر","تزوير","التزوير","إحتيال","إختلاس","ضرر","أضرار","حقوق الإنسان","مقتل","إصابة"
            ,"حقوقهم","احتجاجات","احتج","إهمال","حادث", "إنفجار","خرق","مقتل","إبتزاز","إنتهاك","إحتكار","إرهاب","الإرهاب","الإرهابية","مسلحة","عنصري","تجسس","للتجسس","عمالة أطفال","عنصرية","تشغيل أطفال","تهرب ضريبي","عمل الأطفال","تعسفية","تلوث","غير آدمية","التحايل", "غير إنسانية"));


    public static DocumentData calculateTotalScore(Set<String> words) {
        DocumentData documentData = new DocumentData();
        double nounScore = calculateNoun(words);
        if (nounScore>0) {
            double legalScore = calculateLegal(words);
            double esgScore = calculateEsg(words);
            documentData.setEsgScore((legalScore + esgScore+nounScore)/words.size());
            return documentData;
        } else {
            documentData.setEsgScore(0);
            return  documentData;
        }
    }


    public static double calculateNoun(Set<String> words) {
        double nounScore = 0;
        for (String word: words) {
            if (noun.contains(word)) {
                nounScore += 100;
            }
        }
        return nounScore;
    }

    public static double calculateLegal(Set<String> words) {
        double legalScore = 0;
        for (String word: words) {
            if (legal.contains(word)) {
                legalScore += 100;
            }
        }
        return legalScore;
    }

    public static double calculateEsg(Set<String> words) {
        double esgScore = 0;
        for (String word: words) {
            if (esg.contains(word)) {
                esgScore += 100;
            }
        }
        return esgScore;
    }

    public static List<String> getWordsFromLines(String line) {
        return Arrays.asList(line.split("(\\.)+|(,)+|( )+|(-)+|(\\?)+|(!)+|(;)+|(:)+|(/d)+|(/n)+"));
    }

    public static List<String> getWordsFromDocument(List<String> lines) {
        List<String> words = new ArrayList<>();
        for (String line: lines) {
            words.addAll(getWordsFromLines(line));
        }
        return words;
    }

}

