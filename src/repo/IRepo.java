package repo;

import exceptions.MyRepoException;
import model.state.ProgState;

import java.util.List;

public interface IRepo {
    public List<ProgState> getProgramsList();
    public void setProgramsList(List<ProgState> progState);
    public void addProgState(ProgState progState);
    public void logProgramState(ProgState progState) throws MyRepoException;
    public void setProgList(List<ProgState> progList);
}
