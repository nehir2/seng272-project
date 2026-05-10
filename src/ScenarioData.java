import java.util.ArrayList;
import java.util.HashMap;


public class ScenarioData {


    public static ArrayList<Scenario> getScenarios(String mode, String qualityType) {
        String key = mode + "_" + qualityType;
        ArrayList<Scenario> list = new ArrayList<>();

        switch (key) {
            case "Education_Product":
                list.add(buildEducationProductC());
                list.add(buildEducationProductD());
                break;
            case "Education_Process":
                list.add(buildEducationProcessA());
                list.add(buildEducationProcessB());
                break;
            case "Health_Product":
                list.add(buildHealthProductA());
                list.add(buildHealthProductB());
                break;
            case "Health_Process":
                list.add(buildHealthProcessA());
                list.add(buildHealthProcessB());
                break;
        }
        return list;
    }


    private static Scenario buildEducationProductC() {
        Scenario s = new Scenario("Scenario C — Team Alpha");

        QDimension usability = new QDimension("Usability", 25);
        usability.addMetric(new Metric("SUS Score",        50, true,  0, 100, "points", 89));
        usability.addMetric(new Metric("Onboarding Time",  50, false, 0,  60, "min",     5));

        QDimension perf = new QDimension("Performance Efficiency", 20);
        perf.addMetric(new Metric("Video Start Time",   50, false, 0,  15, "sec",   2));
        perf.addMetric(new Metric("Concurrent Exams",   50, true,  0, 600, "users", 480));

        QDimension access = new QDimension("Accessibility", 20);
        access.addMetric(new Metric("WCAG Compliance",     50, true, 0, 100, "%", 92));
        access.addMetric(new Metric("Screen Reader Score", 50, true, 0, 100, "%", 78));

        QDimension reliability = new QDimension("Reliability", 20);
        reliability.addMetric(new Metric("Uptime", 50, true,  95, 100, "%",   99.5));
        reliability.addMetric(new Metric("MTTR",   50, false,  0, 120, "min", 15));

        QDimension func = new QDimension("Functional Suitability", 15);
        func.addMetric(new Metric("Feature Completion",   50, true, 0, 100, "%", 95));
        func.addMetric(new Metric("Assignment Submit Rate",50, true, 0, 100, "%", 88));

        s.addDimension(usability);
        s.addDimension(perf);
        s.addDimension(access);
        s.addDimension(reliability);
        s.addDimension(func);
        return s;
    }


    private static Scenario buildEducationProductD() {
        Scenario s = new Scenario("Scenario D — Team Beta");

        QDimension usability = new QDimension("Usability", 25);
        usability.addMetric(new Metric("SUS Score",       50, true,  0, 100, "points", 72));
        usability.addMetric(new Metric("Onboarding Time", 50, false, 0,  60, "min",    20));

        QDimension perf = new QDimension("Performance Efficiency", 20);
        perf.addMetric(new Metric("Video Start Time",  50, false, 0,  15, "sec",   8));
        perf.addMetric(new Metric("Concurrent Exams",  50, true,  0, 600, "users", 300));

        QDimension access = new QDimension("Accessibility", 20);
        access.addMetric(new Metric("WCAG Compliance",     50, true, 0, 100, "%", 65));
        access.addMetric(new Metric("Screen Reader Score", 50, true, 0, 100, "%", 55));

        QDimension reliability = new QDimension("Reliability", 20);
        reliability.addMetric(new Metric("Uptime", 50, true,  95, 100, "%",   97.0));
        reliability.addMetric(new Metric("MTTR",   50, false,  0, 120, "min", 45));

        QDimension func = new QDimension("Functional Suitability", 15);
        func.addMetric(new Metric("Feature Completion",    50, true, 0, 100, "%", 80));
        func.addMetric(new Metric("Assignment Submit Rate", 50, true, 0, 100, "%", 70));

        s.addDimension(usability);
        s.addDimension(perf);
        s.addDimension(access);
        s.addDimension(reliability);
        s.addDimension(func);
        return s;
    }


    private static Scenario buildEducationProcessA() {
        Scenario s = new Scenario("Scenario A — Sprint Team Alpha");

        QDimension sprint = new QDimension("Sprint Efficiency", 35);
        sprint.addMetric(new Metric("Velocity",          50, true,  0, 100, "pts",  85));
        sprint.addMetric(new Metric("Sprint Completion",  50, true,  0, 100, "%",   90));

        QDimension codeQ = new QDimension("Code Quality", 35);
        codeQ.addMetric(new Metric("Code Coverage",  50, true,  0, 100, "%",  78));
        codeQ.addMetric(new Metric("Bug Density",    50, false, 0,  20, "bugs/kloc", 3));

        QDimension collab = new QDimension("Team Collaboration", 30);
        collab.addMetric(new Metric("Review Turnaround", 50, false, 0, 48, "hours", 6));
        collab.addMetric(new Metric("Meeting Attendance",50, true,  0, 100, "%",   95));

        s.addDimension(sprint);
        s.addDimension(codeQ);
        s.addDimension(collab);
        return s;
    }


    private static Scenario buildEducationProcessB() {
        Scenario s = new Scenario("Scenario B — Sprint Team Beta");

        QDimension sprint = new QDimension("Sprint Efficiency", 35);
        sprint.addMetric(new Metric("Velocity",          50, true,  0, 100, "pts",  60));
        sprint.addMetric(new Metric("Sprint Completion",  50, true,  0, 100, "%",   70));

        QDimension codeQ = new QDimension("Code Quality", 35);
        codeQ.addMetric(new Metric("Code Coverage", 50, true,  0, 100, "%",  55));
        codeQ.addMetric(new Metric("Bug Density",   50, false, 0,  20, "bugs/kloc", 12));

        QDimension collab = new QDimension("Team Collaboration", 30);
        collab.addMetric(new Metric("Review Turnaround", 50, false, 0, 48, "hours", 36));
        collab.addMetric(new Metric("Meeting Attendance",50, true,  0, 100, "%",   75));

        s.addDimension(sprint);
        s.addDimension(codeQ);
        s.addDimension(collab);
        return s;
    }



    private static Scenario buildHealthProductA() {
        Scenario s = new Scenario("Scenario A — Clinic Pro");

        QDimension usability = new QDimension("Usability", 25);
        usability.addMetric(new Metric("SUS Score",       50, true,  0, 100, "points", 80));
        usability.addMetric(new Metric("Task Success Rate",50, true,  0, 100, "%",     88));

        QDimension security = new QDimension("Security", 30);
        security.addMetric(new Metric("Auth Failure Rate", 50, false, 0, 10, "%",    0.5));
        security.addMetric(new Metric("Data Encryption",   50, true,  0, 100, "%",  100));

        QDimension perf = new QDimension("Performance Efficiency", 20);
        perf.addMetric(new Metric("Page Load Time",  50, false, 0, 10, "sec",  1.2));
        perf.addMetric(new Metric("API Response",    50, false, 0,  2, "sec",  0.3));

        QDimension reliability = new QDimension("Reliability", 25);
        reliability.addMetric(new Metric("Uptime", 50, true,  95, 100, "%",   99.8));
        reliability.addMetric(new Metric("MTTR",   50, false,  0, 120, "min",  10));

        s.addDimension(usability);
        s.addDimension(security);
        s.addDimension(perf);
        s.addDimension(reliability);
        return s;
    }


    private static Scenario buildHealthProductB() {
        Scenario s = new Scenario("Scenario B — MedTrack");

        QDimension usability = new QDimension("Usability", 25);
        usability.addMetric(new Metric("SUS Score",        50, true,  0, 100, "points", 65));
        usability.addMetric(new Metric("Task Success Rate", 50, true,  0, 100, "%",     72));

        QDimension security = new QDimension("Security", 30);
        security.addMetric(new Metric("Auth Failure Rate", 50, false, 0, 10, "%",  2.0));
        security.addMetric(new Metric("Data Encryption",   50, true,  0, 100, "%", 85));

        QDimension perf = new QDimension("Performance Efficiency", 20);
        perf.addMetric(new Metric("Page Load Time", 50, false, 0, 10, "sec", 4.5));
        perf.addMetric(new Metric("API Response",   50, false, 0,  2, "sec", 0.9));

        QDimension reliability = new QDimension("Reliability", 25);
        reliability.addMetric(new Metric("Uptime", 50, true,  95, 100, "%",   97.5));
        reliability.addMetric(new Metric("MTTR",   50, false,  0, 120, "min",  40));

        s.addDimension(usability);
        s.addDimension(security);
        s.addDimension(perf);
        s.addDimension(reliability);
        return s;
    }


    private static Scenario buildHealthProcessA() {
        Scenario s = new Scenario("Scenario A — DevOps Alpha");

        QDimension sprint = new QDimension("Sprint Efficiency", 35);
        sprint.addMetric(new Metric("Velocity",          50, true,  0, 100, "pts", 92));
        sprint.addMetric(new Metric("Sprint Completion",  50, true,  0, 100, "%",  95));

        QDimension codeQ = new QDimension("Code Quality", 35);
        codeQ.addMetric(new Metric("Code Coverage", 50, true,  0, 100, "%",       82));
        codeQ.addMetric(new Metric("Bug Density",   50, false, 0,  20, "bugs/kloc", 2));

        QDimension collab = new QDimension("Team Collaboration", 30);
        collab.addMetric(new Metric("Review Turnaround", 50, false, 0, 48, "hours",  4));
        collab.addMetric(new Metric("Meeting Attendance",50, true,  0, 100, "%",    98));

        s.addDimension(sprint);
        s.addDimension(codeQ);
        s.addDimension(collab);
        return s;
    }


    private static Scenario buildHealthProcessB() {
        Scenario s = new Scenario("Scenario B — DevOps Beta");

        QDimension sprint = new QDimension("Sprint Efficiency", 35);
        sprint.addMetric(new Metric("Velocity",          50, true,  0, 100, "pts", 55));
        sprint.addMetric(new Metric("Sprint Completion",  50, true,  0, 100, "%",  60));

        QDimension codeQ = new QDimension("Code Quality", 35);
        codeQ.addMetric(new Metric("Code Coverage", 50, true,  0, 100, "%",       45));
        codeQ.addMetric(new Metric("Bug Density",   50, false, 0,  20, "bugs/kloc", 15));

        QDimension collab = new QDimension("Team Collaboration", 30);
        collab.addMetric(new Metric("Review Turnaround", 50, false, 0, 48, "hours", 40));
        collab.addMetric(new Metric("Meeting Attendance",50, true,  0, 100, "%",    68));

        s.addDimension(sprint);
        s.addDimension(codeQ);
        s.addDimension(collab);
        return s;
    }
}