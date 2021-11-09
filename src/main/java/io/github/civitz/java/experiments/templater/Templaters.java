package io.github.civitz.java.experiments.templater;

import io.vavr.collection.Map;
import io.vavr.collection.Set;

public class Templaters {


    public static String naiveRegexReplace(String template, Map<String, String> values) {
        return values.foldLeft(template, (replaced, kv) -> replaced.replaceAll("\\[[\\s]*" + kv._1 + "[\\s]*\\]", kv._2));
    }

    public static String scrolling(String template, Map<String, String> values) {
        int pendingBracketPos = -1;
        StringBuffer buffer=new StringBuffer(template.length());
        for(int pos = 0; pos<template.length();pos++){
            char c = template.charAt(pos);
            if(c=='['){
                if(pendingBracketPos==-1) {
                    pendingBracketPos = pos;
                }else{
                    buffer.append(template.substring(pendingBracketPos,pos));
                }
            } else if (c == ']') {
                if(pendingBracketPos==-1){
                    buffer.append(c);
                }else {
                    String possibleKey = template.substring(pendingBracketPos + 1, pos).trim();
                    if(values.containsKey(possibleKey)){
                        buffer.append(values.get(possibleKey).get());
                        pendingBracketPos = -1;
                    }else{
                        buffer.append(template.substring(pendingBracketPos, pos+1));
                        pendingBracketPos = -1;
                    }
                }
            } else if(pendingBracketPos==-1){
                buffer.append(c);
            }
        }
        return buffer.toString();
    }


}
