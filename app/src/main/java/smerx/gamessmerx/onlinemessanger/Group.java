package smerx.gamessmerx.onlinemessanger;

import java.util.ArrayList;

public class Group {

    private ArrayList<String> base;
    private String teacher, code;
    private ArrayList<String> GroupStudentsBase;

    public Group(ArrayList<String> base, String code){
      this.base = base;
      this.code = code;

        for (int i = 0; i < base.size(); i++) {
            if (getInf("Code",base.get(i)).equals(code) && getInf("Type",base.get(i)).equals("student"))
            {
                GroupStudentsBase.add(base.get(i));
            }
            if (getInf("Code",base.get(i)).equals(code) && getInf("Type",base.get(i)).equals("teacher"))
            {
                teacher = base.get(i);
            }
        }
    }

    public String[] getNamesGroup(String login){
        String[] out = new String[GroupStudentsBase.size()];
        for (int i = 0; i < GroupStudentsBase.size(); i++) {
          if (!getInf("Login", GroupStudentsBase.get(i)).equals(login)){
              out[i] = GroupStudentsBase.get(i);
          }
        }
        return out;
    }

    public String getTeacher() {
        return teacher;
    }

    public ArrayList<String> getGroupStudentsBase() {

        return GroupStudentsBase;
    }


    public static String getInf(String inf, String st){
        String answer = "";
        Integer endi = st.length();
        for (int i = 0; i < endi; i++) {
            if (st.startsWith(inf)) {
                for (int j = 0; j < st.length(); i++) {
                    if (st.startsWith("{")) {
                        st = st.substring(1);
                        while (!st.startsWith("}"))
                        {answer += st.charAt(0); st = st.substring(1);}
                        return answer;
                    } else {st = st.substring(1);}
                }
            }
            else {st=st.substring(1);}
        }

        return "NO";
    }
}
